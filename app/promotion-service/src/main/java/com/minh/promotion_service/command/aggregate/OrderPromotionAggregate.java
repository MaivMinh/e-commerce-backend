package com.minh.promotion_service.command.aggregate;

import com.minh.common.commands.ApplyPromotionCommand;
import com.minh.common.events.PromotionAppliedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderPromotionAggregate {
  @AggregateIdentifier
  private String orderPromotionId;
  private String orderId;
  private String promotionId;

  public OrderPromotionAggregate() {
    // Default constructor for Axon framework
  }

  @CommandHandler
  public OrderPromotionAggregate(ApplyPromotionCommand command) {
    /// Validation if needed

    /// Create new event.
    PromotionAppliedEvent event = new PromotionAppliedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(PromotionAppliedEvent event) {
    this.orderPromotionId = event.getOrderPromotionId();
    this.orderId = event.getOrderId();
    this.promotionId = event.getPromotionId();
  }
}
