package com.minh.promotion_service.command.aggregate;

import com.minh.promotion_service.command.commands.CreatePromotionCommand;
import com.minh.promotion_service.command.commands.DeletePromotionCommand;
import com.minh.promotion_service.command.commands.UpdatePromotionCommand;
import com.minh.promotion_service.command.events.PromotionCreatedEvent;
import com.minh.promotion_service.command.events.PromotionDeletedEvent;
import com.minh.promotion_service.command.events.PromotionUpdatedEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;

@Aggregate
@Slf4j
public class PromotionAggregate {
  @AggregateIdentifier
  private String promotionId;
  private String code;
  private String type;
  private Double discountValue;
  private Double minOrderValue;
  private Timestamp startDate;
  private Timestamp endDate;
  private Integer usageLimit;
  private Integer usageCount;
  private String status;
  private String errorMsg;

  public PromotionAggregate() {
    // Default constructor for Axon Framework
  }

  @CommandHandler
  public PromotionAggregate(CreatePromotionCommand command) {
    /// Validate if needed.
    /// Create new event.
    PromotionCreatedEvent event = new PromotionCreatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PromotionCreatedEvent event) {
    this.promotionId = event.getPromotionId();
    this.code = event.getCode();
    this.type = event.getType();
    this.discountValue = event.getDiscountValue();
    this.minOrderValue = event.getMinOrderValue();
    this.startDate = event.getStartDate();
    this.endDate = event.getEndDate();
    this.usageLimit = event.getUsageLimit();
    this.usageCount = event.getUsageCount();
    this.status = event.getStatus();
  }


  @CommandHandler
  public void handle(UpdatePromotionCommand command) {
    /// Validate if needed.
    /// Create new event.
    PromotionUpdatedEvent event = new PromotionUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PromotionUpdatedEvent event) {
    this.code = event.getCode();
    this.type = event.getType();
    this.discountValue = event.getDiscountValue();
    this.minOrderValue = event.getMinOrderValue();
    this.startDate = event.getStartDate();
    this.endDate = event.getEndDate();
    this.usageLimit = event.getUsageLimit();
    this.usageCount = event.getUsageCount();
    this.status = event.getStatus();
  }

  @CommandHandler
  public void handle(DeletePromotionCommand command) {
    /// Validate if needed.
    /// Create new event.
    PromotionDeletedEvent event = new PromotionDeletedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }
  @EventSourcingHandler
  public void on(PromotionDeletedEvent event) {
    this.status = "inactive"; // Mark as inactive instead of deleting
  }
}