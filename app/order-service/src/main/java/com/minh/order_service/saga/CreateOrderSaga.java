package com.minh.order_service.saga;

import com.minh.common.commands.*;
import com.minh.common.dto.ReserveProductItem;
import com.minh.common.events.*;
import com.minh.order_service.query.queries.FindOverallStatusOfCreatingOrderQuery;
import com.minh.order_service.response.ResponseData;
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
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import java.util.*;

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

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentProcessedEvent event) {
    log.info("Saga Event 4 : Payment processed successfully for order id: {}", event.getOrderId());
    /// Tiếp tục quay lại để cập nhật trạng thái bên product-servcie & order-service.
    /// Còn promotion-service thì đã được cập nhật trạng thái trong sự kiện PromotionAppliedEvent. Còn khi failed sẽ thuộc về Rollback saga.

    /// Confirm reserved products command.
    ConfirmReserveProductCommand command = ConfirmReserveProductCommand.builder()
            .reserveProductId(event.getReserveProductId())
            .paymentId(event.getPaymentId())
            .orderPromotionId(event.getOrderPromotionId())
            .orderId(event.getOrderId())
            .build();

    commandGateway.send(command, new CommandCallback<>() {
      @Override
      public void onResult(@Nonnull CommandMessage<? extends ConfirmReserveProductCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.isExceptional()) {
          log.error("Saga Event 4 [Error] : Failed to confirm reserved product for payment id: {}, rolling back order creation.", event.getPaymentId());
          RollbackProcessPaymentCommand rollbackCommand = RollbackProcessPaymentCommand.builder()
                  .paymentId(event.getPaymentId())
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

  @SagaEventHandler(associationProperty = "orderId")
  public void on(ReserveProductConfirmedEvent event) {
    log.info("Saga Event 5 : Reserve product confirmed for order id: {}", event.getOrderId());
    /// Xác nhận đặt chỗ sản phẩm thành công, cập nhật trạng thái đơn hàng trong order-service.
    ConfirmCreateOrderCommand command = ConfirmCreateOrderCommand.builder()
            .orderId(event.getOrderId())
            .paymentId(event.getPaymentId())
            .orderPromotionId(event.getOrderPromotionId())
            .reserveProductId(event.getReserveProductId())
            .build();

    commandGateway.send(command, new CommandCallback<>() {
      @Override
      public void onResult(@Nonnull CommandMessage<? extends ConfirmCreateOrderCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.isExceptional()) {
          log.error("Saga Event 5 [Error] : Failed to confirm created order for order id: {}, rolling back payment process.", event.getOrderId());
          RollbackProcessPaymentCommand rollbackCommand = RollbackProcessPaymentCommand.builder()
                  .paymentId(event.getPaymentId())
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
  public void on(CreateOrderConfirmedEvent event) {
    log.info("Saga Event 6 [End] : Create order confirmed for order id: {}", event.getOrderId());
    /// Cập nhật trạng thái đơn hàng thành công trong order-service.
    /// Emit an update to notify the query side about the order creation.

    Map<String, String> data = new HashMap<>();
    data.put("orderId", event.getOrderId());
    data.put("paymentId", event.getPaymentId());
    queryUpdateEmitter.emit(
            FindOverallStatusOfCreatingOrderQuery.class,
            query -> true,  /// predicate function to filter updates.
            ResponseData.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Order created successfully")
                    .data(data)
                    .build()
    );
  }

  /// ====================================== ROLLBACK SAGA HANDLERS ====================================== ///


  @SagaEventHandler(associationProperty = "orderId")
  public void on(ProcessPaymentRollbackedEvent event) {
    log.info("Saga Event Rollback [1] : Received ProcessPaymentRollbackedEvent for order id: {}", event.getOrderId());

    /// Rollback the applied promotion command.
    RollbackApplyPromotionCommand rollbackCommand = RollbackApplyPromotionCommand.builder()
            .orderPromotionId(event.getOrderPromotionId())
            .reserveProductId(event.getReserveProductId())
            .orderId(event.getOrderId())
            .errorMsg(event.getErrorMsg())
            .build();
    commandGateway.sendAndWait(rollbackCommand);
  }


  @SagaEventHandler(associationProperty = "orderId")
  public void on(PromotionApplyRollbackedEvent event) {
    log.info("Saga Event Rollback [2] : Received PromotionApplyRollbackedEvent for order id: {}", event.getOrderId());
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
    RollbackCreateOrderCommand rollbackCommand = RollbackCreateOrderCommand.builder()
            .orderId(event.getOrderId())
            .errorMsg(event.getErrorMsg())
            .build();
    commandGateway.sendAndWait(rollbackCommand);
  }


  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderCreateRollbackedEvent event) {
    log.info("Saga Event Rollback [END]: Received OrderCreateRollbackedEvent for order id: {}", event.getOrderId());

    Map<String, String> data = new HashMap<>();
    data.put("orderId", event.getOrderId());
    data.put("errorMsg", event.getErrorMsg());

    queryUpdateEmitter.emit(
            FindOverallStatusOfCreatingOrderQuery.class,
            query -> true,  /// predicate function to filter updates.
            ResponseData.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Order creation failed and rolled back")
                    .data(data)
                    .build()
    );
  }
}