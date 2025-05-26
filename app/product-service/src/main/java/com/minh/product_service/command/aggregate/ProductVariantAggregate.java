package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.CreateCategoryCommand;
import com.minh.product_service.command.commands.CreateProductVariantCommand;
import com.minh.product_service.command.commands.DeleteProductVariantCommand;
import com.minh.product_service.command.commands.UpdateProductVariantCommand;
import com.minh.product_service.command.events.ProductVariantCreatedEvent;
import com.minh.product_service.command.events.ProductVariantDeletedEvent;
import com.minh.product_service.command.events.ProductVariantUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
public class ProductVariantAggregate {
  @AggregateIdentifier
  private String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;
  private String errorMsg;

  public ProductVariantAggregate() {
    // Default constructor for Axon Framework.
  }

  @CommandHandler
  public ProductVariantAggregate(CreateProductVariantCommand command) {
    /// Create new events.
    ProductVariantCreatedEvent event = new ProductVariantCreatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit events.
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(ProductVariantCreatedEvent event) {
    this.id = event.getId();
    this.productId = event.getProductId();
    this.size = event.getSize();
    this.colorName = event.getColorName();
    this.colorHex = event.getColorHex();
    this.price = event.getPrice();
    this.quantity = event.getQuantity();
  }


  @CommandHandler
  public void handle(UpdateProductVariantCommand command) {
    ProductVariantUpdatedEvent event = new ProductVariantUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(ProductVariantUpdatedEvent event) {
    this.productId = event.getProductId();
    this.size = event.getSize();
    this.colorName = event.getColorName();
    this.colorHex = event.getColorHex();
    this.price = event.getPrice();
    this.quantity = event.getQuantity();
  }



  @CommandHandler
  public void handle(DeleteProductVariantCommand command) {
    ProductVariantDeletedEvent event = new ProductVariantDeletedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(ProductVariantDeletedEvent event) {
    this.colorName = null;
    this.colorHex = null;
    this.size = null;
    this.price = null;
    this.quantity = null;
  }
}
