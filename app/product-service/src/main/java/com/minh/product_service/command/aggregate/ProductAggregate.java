package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.CreateProductCommand;
import com.minh.product_service.command.commands.DeleteProductCommand;
import com.minh.product_service.command.commands.UpdateProductCommand;
import com.minh.product_service.command.events.ProductCreatedEvent;
import com.minh.product_service.command.events.ProductDeletedEvent;
import com.minh.product_service.command.events.ProductUpdatedEvent;
import com.minh.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
public class ProductAggregate {
  @AggregateIdentifier
  private String id;
  private String name;
  private String description;
  private String cover;
  private String errorMsg;

  public ProductAggregate() {
    /// Default constructor is required by Axon Framework
  }

  @CommandHandler
  public ProductAggregate(CreateProductCommand command) {
    /// Create new Event.
    ProductCreatedEvent event = new ProductCreatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit events to events bus and events store.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductCreatedEvent event) {
    this.id = event.getId();
    this.name = event.getName();
    this.description = event.getDescription();
    this.cover = event.getCover();
  }


  @CommandHandler
  public void handle(UpdateProductCommand command) {
    /// Create new events.
    ProductUpdatedEvent event = new ProductUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit events.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductUpdatedEvent event) {
    this.name = event.getName();
    this.description = event.getDescription();
    this.cover = event.getCover();
  }

  @CommandHandler
  public void handle(DeleteProductCommand command) {
    /// Create new events.
    ProductDeletedEvent event = new ProductDeletedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit events.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductDeletedEvent event) {
    this.name = null;
    this.description = null;
    this.cover = null;
    this.errorMsg = null;
  }
}
