package com.minh.payment_service.command.aggregate;

import com.minh.common.commands.ProcessPaymentCommand;
import com.minh.common.commands.RollbackProcessPaymentCommand;
import com.minh.common.events.PaymentProcessedEvent;
import com.minh.common.events.ProcessPaymentRollbackedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class PaymentAggregate {
  @AggregateIdentifier
  private String paymentId;
  private String orderPromotionId;
  private String reserveProductId;
  private String orderId;
  private String paymentMethodId;
  private Double amount;
  private String status;
  private String transactionId;
  private String currency;
  private String errorMsg;


  public PaymentAggregate() {
    // Default constructor for Axon framework
  }

  @CommandHandler
  public PaymentAggregate(ProcessPaymentCommand command) {
    /// Validate if needed.
    ///  Create new event.
    PaymentProcessedEvent event = new PaymentProcessedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PaymentProcessedEvent event) {
    this.paymentId = event.getPaymentId();
    this.orderId = event.getOrderId();
    this.paymentMethodId = event.getPaymentMethodId();
    this.amount = event.getAmount();
    this.currency = event.getCurrency();
    this.orderPromotionId = event.getOrderPromotionId();
    this.reserveProductId = event.getReserveProductId();
  }

  @CommandHandler
  public void handle(RollbackProcessPaymentCommand command) {
    /// Create new event for rollback.
    ProcessPaymentRollbackedEvent event = new ProcessPaymentRollbackedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(ProcessPaymentRollbackedEvent event) {
    this.errorMsg = event.getErrorMsg();
  }
}
