package com.minh.order_service.saga;

import com.minh.common.commands.*;
import com.minh.common.dto.ReserveProductItem;
import com.minh.common.events.*;
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
            .promotionId(event.getPromotionId())
            .paymentMethodId(event.getPaymentMethodId())
            .reserveProductItems(reserveProductItems)
            .amount(event.getTotal())
            .currency(event.getCurrency())
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

  @SagaEventHandler(associationProperty = "orderId")
  public void on(ProductReservedEvent event) {
    log.info("Saga Event 2 : Received ProductReservedEvent for order id: {}", event.getOrderId());
    /// Apply promotion for the order if needed.
    ApplyPromotionCommand command = ApplyPromotionCommand.builder()
            .orderPromotionId(UUID.randomUUID().toString())
            .reserveProductId(event.getReserveProductId())
            .orderId(event.getOrderId())
            .promotionId(event.getPromotionId())
            .paymentMethodId(event.getPaymentMethodId())
            .amount(event.getAmount())
            .currency(event.getCurrency())
            .build();

    commandGateway.send(command, new CommandCallback<>() {
      @Override
      public void onResult(@Nonnull CommandMessage<? extends ApplyPromotionCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.isExceptional()) {
          log.error("Saga Event 2 [Error] : Failed to apply promotion for order id: {}, rolling back order creation.", event.getOrderId());
          RollbackReserveProductCommand rollbackCommand = RollbackReserveProductCommand.builder()
                  .reserveProductId(event.getReserveProductId())
                  .orderId(event.getOrderId())
                  .errorMsg(commandResultMessage.exceptionResult().getMessage())
                  .build();
          commandGateway.sendAndWait(rollbackCommand);
        }
      }
    });
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PromotionAppliedEvent event) {
    log.info("Saga Event 3 : Received PromotionAppliedEvent for order id: {}", event.getOrderId());
    /// Kết thúc saga sau khi đã áp dụng khuyến mãi thành công.
    /// Process payment.
    ProcessPaymentCommand command = ProcessPaymentCommand.builder()
            .paymentId(UUID.randomUUID().toString())
            .orderPromotionId(event.getOrderPromotionId())
            .reserveProductId(event.getReserveProductId())
            .orderId(event.getOrderId())
            .paymentMethodId(event.getPaymentMethodId())
            .amount(event.getAmount())
            .currency(event.getCurrency())
            .build();

    commandGateway.send(command, new CommandCallback<>() {
      @Override
      public void onResult(@Nonnull CommandMessage<? extends ProcessPaymentCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.isExceptional()) {
          log.error("Saga Event 3 [Error] : Failed to process payment for order id: {}, rolling back order creation.", event.getOrderId());
          RollbackApplyPromotionCommand rollbackCommand = RollbackApplyPromotionCommand.builder()
                  .orderPromotionId(event.getOrderPromotionId())
                  .reserveProductId(event.getReserveProductId())
                  .orderId(event.getOrderId())
                  .errorMsg(commandResultMessage.exceptionResult().getMessage())
                  .build();
          commandGateway.sendAndWait(rollbackCommand);
        }
      }
    });
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentProcessedEvent event) {
    log.info("Saga Event 4 [End] : Payment processed successfully for order id: {}", event.getOrderId());
    /// Kết thúc saga sau khi thanh toán thành công.
  }



  @SagaEventHandler(associationProperty = "orderId")
  public void on(PromotionApplyRollbackedEvent event) {
    log.info("Saga Event Rollback [1] : Received PromotionApplyRollbackedEvent for order id: {}", event.getOrderId());
    /// Rollback the reserve product command.
    RollbackReserveProductCommand rollbackCommand = RollbackReserveProductCommand.builder()
            .reserveProductId(event.getReserveProductId())
            .orderId(event.getOrderId())
            .errorMsg(event.getErrorMsg())
            .build();
    commandGateway.sendAndWait(rollbackCommand);
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(ProductReserveRollbackedEvent event) {
    log.info("Saga Event Rollback [2]: Received ProductReserveRollbackedEvent for order id: {}", event.getOrderId());
    /// Rollback the order creation.
    RollbackCreateOrderCommand rollbackCommand = new RollbackCreateOrderCommand();
    rollbackCommand.setOrderId(event.getOrderId());
    rollbackCommand.setErrorMsg(event.getErrorMsg());
    commandGateway.sendAndWait(rollbackCommand);
  }


  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderCreateRollbackedEvent event) {
    log.info("Saga Event Rollback [END]: Received OrderCreateRollbackedEvent for order id: {}", event.getOrderId());
  }
}