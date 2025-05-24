package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.CreateCustomerCommand;
import com.minh.product_service.command.commands.UpdateCustomerCommand;
import com.minh.product_service.command.events.CustomerCreatedEvent;
import com.minh.product_service.command.events.CustomerUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CustomerAggregate {
  @AggregateIdentifier
  private String customerId;
  private String name;
  private String email;
  private String mobileNumber;
  private boolean activeSw;
  private String errorMsg;

  public CustomerAggregate() {

  }

  @CommandHandler
  public CustomerAggregate(CreateCustomerCommand command) {
    System.out.println("CreateCustomerCommand method called");
    /// Create new Event.
    CustomerCreatedEvent event = new CustomerCreatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit events to events bus and events store.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CustomerCreatedEvent event) {
    System.out.println("CustomerCreatedEvent method called");
    this.customerId = event.getCustomerId();
    this.name = event.getName();
    this.email = event.getEmail();
    this.mobileNumber = event.getMobileNumber();
    this.activeSw = event.isActiveSw();
  }

  @CommandHandler
  public void handle(UpdateCustomerCommand command) {
    CustomerUpdatedEvent event = new CustomerUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CustomerUpdatedEvent event) {
    this.name = event.getName();
    this.email = event.getEmail();
    this.mobileNumber = event.getMobileNumber();
  }
}
