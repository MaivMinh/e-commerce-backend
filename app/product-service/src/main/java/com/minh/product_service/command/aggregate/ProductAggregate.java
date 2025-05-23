package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.CreateProductCommand;
import com.minh.product_service.command.commands.DeleteProductCommand;
import com.minh.product_service.command.commands.UpdateProductCommand;
import com.minh.product_service.command.event.ProductCreatedEvent;
import com.minh.product_service.command.event.ProductDeletedEvent;
import com.minh.product_service.command.event.ProductUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

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
    /// If validation in Interceptor successfully, we can create new Event to save into event Store and emit to event Bus

    /// Create new Event.
    ProductCreatedEvent event = new ProductCreatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit event to event bus and event store.
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
    /// Create new event.
    ProductUpdatedEvent event = new ProductUpdatedEvent();
    BeanUtils.copyProperties(command, event);

    /// Emit event.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductUpdatedEvent event) {
    this.id = event.getId();
    this.name = event.getName();
    this.description = event.getDescription();
    this.cover = event.getCover();
  }

  @CommandHandler
  public void handle(DeleteProductCommand command) {
    /// Create new event.
    ProductDeletedEvent event = new ProductDeletedEvent();
    BeanUtils.copyProperties(command, event);

    /// Emit event.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductDeletedEvent event) {
    this.id = event.getId();
    this.name = null;
    this.description = null;
    this.cover = null;
  }
}
