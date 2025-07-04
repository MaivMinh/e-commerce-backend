package com.minh.order_service.command.aggregate;

import com.minh.common.commands.ConfirmCreateOrderCommand;
import com.minh.common.commands.CreateOrderCommand;
import com.minh.common.commands.RollbackCreateOrderCommand;
import com.minh.common.dto.OrderItemCreateDTO;
import com.minh.common.events.CreateOrderConfirmedEvent;
import com.minh.common.events.OrderCreateRollbackedEvent;
import com.minh.common.events.OrderCreatedEvent;
import com.minh.order_service.entity.OrderStatus;
import com.minh.order_service.entity.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
@Aggregate
public class OrderAggregate {
  @AggregateIdentifier
  private String orderId;
  private String accountId;
  private String shippingAddressId;
  private String orderStatus;
  private Double subTotal;
  private Double discount;
  private Double total;
  private String paymentMethodId;
  private String paymentId;
  private String paymentStatus;
  private String promotionId;
  private String currency;
  private String note;
  private List<OrderItemCreateDTO> orderItemDTOs;
  private String orderPromotionId;
  private String reserveProductId;
  private String errorMsg;

  public OrderAggregate() {
    // Default constructor for Axon framework
  }

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    log.info("Handling CreateOrderCommand for orderId: {}", command.getOrderId());
    /// Handle validation in the interceptor.
    /// Create new event OrderCreatedEvent
    OrderCreatedEvent event = new OrderCreatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    /// Set the properties of the aggregate from the event to store event into event store.
    this.orderId = event.getOrderId();
    this.accountId = event.getAccountId();
    this.shippingAddressId = event.getShippingAddressId();
    this.orderStatus = OrderStatus.pending.toString();
    this.subTotal = event.getSubTotal();
    this.discount = event.getDiscount();
    this.total = event.getTotal();
    this.paymentMethodId = event.getPaymentMethodId();
    this.paymentId = null;
    this.paymentStatus = PaymentStatus.pending.toString();
    this.currency = event.getCurrency();
    this.promotionId = event.getPromotionId();
    this.note = event.getNote();
    this.orderItemDTOs = event.getOrderItemDTOs();
    this.orderPromotionId = null;
    this.reserveProductId = null;
    this.errorMsg = null;
  }


  @CommandHandler
  public void handle(RollbackCreateOrderCommand command) {
    log.info("Handling RollbackCreateOrderCommand for orderId: {}", command.getOrderId());
    /// create new event
    OrderCreateRollbackedEvent event = new OrderCreateRollbackedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(OrderCreateRollbackedEvent event) {
    /// Set the properties of the aggregate from the event to store event into event store.
    this.errorMsg = event.getErrorMsg();
  }

  @CommandHandler
  public void handle(ConfirmCreateOrderCommand command) {
    log.info("Handling ConfirmCreateOrderCommand for orderId: {}", command.getOrderId());
    /// Create new event to confirm the order creation.
    CreateOrderConfirmedEvent event = new CreateOrderConfirmedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CreateOrderConfirmedEvent event) {
    log.info("Confirming order creation for orderId: {}", event.getOrderId());
    /// Set the properties of the aggregate from the event to store event into event store.
    this.paymentId = event.getPaymentId();
    this.orderPromotionId = event.getOrderPromotionId();
    this.reserveProductId = event.getReserveProductId();
    this.errorMsg = event.getErrorMsg();
  }
}