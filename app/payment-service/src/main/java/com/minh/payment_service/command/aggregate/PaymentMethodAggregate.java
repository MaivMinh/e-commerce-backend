package com.minh.payment_service.command.aggregate;


import com.minh.payment_service.command.commands.CreatePaymentMethodCommand;
import com.minh.payment_service.command.commands.DeletePaymentMethodCommand;
import com.minh.payment_service.command.commands.UpdatePaymentMethodCommand;
import com.minh.payment_service.command.events.PaymentMethodCreatedEvent;
import com.minh.payment_service.command.events.PaymentMethodDeletedEvent;
import com.minh.payment_service.command.events.PaymentMethodUpdatedEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentMethodAggregate {
  @AggregateIdentifier
  private String paymentMethodId;
  private String code;
  private String name;
  private String description;
  private String type;
  private String provider;
  private String currency;
  private Boolean isActive;
  private String iconUrl;
  private String errorMsg;

  public PaymentMethodAggregate() {
    // Default constructor for Axon framework
  }

  @CommandHandler
  public PaymentMethodAggregate(CreatePaymentMethodCommand command) {
    // Handle validation in the interceptor.
    // Create new event PaymentMethodCreatedEvent
    PaymentMethodCreatedEvent event = new PaymentMethodCreatedEvent();
    BeanUtils.copyProperties(command, event);
    // Apply the event to the aggregate
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PaymentMethodCreatedEvent event) {
    // Set the properties of the aggregate from the event to store event into event store.
    this.paymentMethodId = event.getPaymentMethodId();
    this.code = event.getCode();
    this.name = event.getName();
    this.description = event.getDescription();
    this.type = event.getType();
    this.provider = event.getProvider();
    this.currency = event.getCurrency();
    this.iconUrl = event.getIconUrl();
    this.isActive = event.getIsActive(); // Default to active when created
  }

  @CommandHandler
  public void handle(UpdatePaymentMethodCommand command) {
    // Handle validation in the interceptor.
    // Create new event PaymentMethodUpdatedEvent
    PaymentMethodUpdatedEvent event = new PaymentMethodUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    // Apply the event to the aggregate
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PaymentMethodUpdatedEvent event) {
    // Update the properties of the aggregate from the event
    this.code = event.getCode();
    this.name = event.getName();
    this.description = event.getDescription();
    this.type = event.getType();
    this.provider = event.getProvider();
    this.currency = event.getCurrency();
    this.iconUrl = event.getIconUrl();
    this.isActive = event.getIsActive(); // Default to active when created
  }

  @CommandHandler
  public void handle(DeletePaymentMethodCommand command) {
    // Handle validation in the interceptor.
    // Create new event PaymentMethodDeletedEvent
    PaymentMethodDeletedEvent event = new PaymentMethodDeletedEvent();
    BeanUtils.copyProperties(command, event);
    // Apply the event to the aggregate
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PaymentMethodDeletedEvent event) {
    // Set the properties of the aggregate from the event to store event into event store.
    this.isActive = false; // Mark as inactive when deleted
  }
}
