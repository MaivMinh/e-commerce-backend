package com.minh.product_service.command.aggregate;

import com.minh.common.commands.ReserveProductCommand;
import com.minh.common.dto.ReserveProductItem;
import com.minh.common.events.ProductReservedEvent;
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
public class ReserveProductAggregate {
  @AggregateIdentifier
  private String reserveProductId;
  private String orderId;
  private List<ReserveProductItem> reserveProductItems;
  private String promotionId;
  private String accountId;
  private String errorMsg;


  public ReserveProductAggregate() {
    /// Default constructor is required by Axon Framework
  }

  @CommandHandler
  public ReserveProductAggregate(ReserveProductCommand command) {
    log.info("Handling ReserveProductCommand for orderId: {}", command.getReserveProductId());
    /// create new event.
    ProductReservedEvent event = new ProductReservedEvent();
    BeanUtils.copyProperties(command, event);
    /// Emit event to events bus and events store.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(ProductReservedEvent event) {
    this.reserveProductId = event.getReserveProductId();
    this.orderId = event.getOrderId();
    this.reserveProductItems = event.getReserveProductItems();
    this.promotionId = event.getPromotionId();
    this.accountId = event.getAccountId();
  }
}