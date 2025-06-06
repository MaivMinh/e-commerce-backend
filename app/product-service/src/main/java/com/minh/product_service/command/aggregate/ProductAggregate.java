package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.*;
import com.minh.product_service.command.events.ProductCreatedEvent;
import com.minh.product_service.command.events.ProductDeletedEvent;
import com.minh.product_service.command.events.ProductUpdatedEvent;
import com.minh.product_service.dto.ProductVariantDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aggregate
public class ProductAggregate {
  @AggregateIdentifier
  private String id;
  private String name;
  private String slug;
  private String description;
  private String cover;
  private List<String> images;
  private Double price;
  private Double originalPrice;
  private List<ProductVariantDTO> productVariants;
  private String status;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
  private String categoryId;
  private String errorMsg;

  public ProductAggregate() {
    /// Default constructor is required by Axon Framework
  }

  @CommandHandler
  public ProductAggregate(CreateProductCommand command) {
    // Create new Event.
    ProductCreatedEvent event = new ProductCreatedEvent();
    BeanUtils.copyProperties(command, event);
    // Emit events to events bus and events store.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductCreatedEvent event) {
    this.id = event.getId();
    this.name = event.getName();
    this.slug = event.getSlug();
    this.description = event.getDescription();
    this.cover = event.getCover();
    this.images = event.getImages();
    this.price = event.getPrice();
    this.originalPrice = event.getOriginalPrice();
    this.isFeatured = event.getIsFeatured();
    this.isNew = event.getIsNew();
    this.isBestseller = event.getIsBestseller();
    this.status = event.getStatus();
    this.categoryId = event.getCategoryId();
    this.productVariants = event.getProductVariants();
    this.errorMsg = null;
  }


  @CommandHandler
  public void handle(UpdateProductCommand command) {
    // Create new events
    ProductUpdatedEvent event = new ProductUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    // Emit events
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductUpdatedEvent event) {
    this.name = event.getName();
    this.slug = event.getSlug();
    this.description = event.getDescription();
    this.cover = event.getCover();
    this.images = event.getImages();
    this.price = event.getPrice();
    this.originalPrice = event.getOriginalPrice();
    this.isFeatured = event.getIsFeatured();
    this.isNew = event.getIsNew();
    this.isBestseller = event.getIsBestseller();
    this.status = event.getStatus();
    this.categoryId = event.getCategoryId();
    this.productVariants = event.getProductVariants();
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
  }
}