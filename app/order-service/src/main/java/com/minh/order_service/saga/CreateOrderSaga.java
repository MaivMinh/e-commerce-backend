package com.minh.order_service.saga;

import com.minh.common.commands.ReserveProductCommand;
import com.minh.common.commands.RollbackCreateOrderCommand;
import com.minh.common.dto.ReserveProductItem;
import com.minh.common.events.OrderCreatedEvent;
import com.minh.common.events.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Saga
@Slf4j
public class CreateOrderSaga {
  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient QueryUpdateEmitter queryUpdateEmitter;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderCreatedEvent event) {
    log.info("Saga Event 1 [Start] : Received OrderCreatedEvent for order id: {}", event.getOrderId());

    /// Thực hiện tạo các command khác và gửi tới cho các service sau.
    /// Thực hiện gửi command để kiểm tra số lượng tồn kho trong product-service.
    List<ReserveProductItem> reserveProductItems = new ArrayList<>();
    event.getOrderItemDTOs().forEach(orderItemDTO -> {
      ReserveProductItem reserveProductItem = new ReserveProductItem();
      reserveProductItem.setProductVariantId(orderItemDTO.getProductVariantId());
      reserveProductItem.setQuantity(orderItemDTO.getQuantity());
      reserveProductItems.add(reserveProductItem);
    });

    ReserveProductCommand command = ReserveProductCommand.builder()
            .reserveProductId(UUID.randomUUID().toString())
            .orderId(event.getOrderId())
            .reserveProductItems(reserveProductItems)
            .promotionId(event.getPromotionId())
            .accountId(event.getAccountId())
            .build();

    commandGateway.send(command, new CommandCallback<>() {
      @Override
      public void onResult(@Nonnull CommandMessage<? extends ReserveProductCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.isExceptional()) {
          RollbackCreateOrderCommand rollbackCommand = new RollbackCreateOrderCommand();
          rollbackCommand.setOrderId(event.getOrderId());
          rollbackCommand.setErrorMsg(commandResultMessage.exceptionResult().getMessage());
          log.error("Saga Event 1 [Error] : Failed to reserve product for order id: {}, rolling back order creation.", event.getOrderId());
          commandGateway.sendAndWait(rollbackCommand);
        }
      }
    });

  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(ProductReservedEvent event) {
    log.info("Saga Event 2 : Received ProductReservedEvent for order id: {}", event.getOrderId());
    /// Emit event to QueryUpdateEmitter to notify other services.
  }
}