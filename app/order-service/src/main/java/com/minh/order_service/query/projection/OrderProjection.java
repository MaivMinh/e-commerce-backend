package com.minh.order_service.query.projection;

import com.minh.common.events.CreateOrderConfirmedEvent;
import com.minh.common.events.OrderCreateRollbackedEvent;
import com.minh.common.events.OrderCreatedEvent;
import com.minh.order_service.query.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("order-group")
@Slf4j
public class OrderProjection {
  private final OrderService orderService;

  @EventHandler
  public void on(OrderCreatedEvent event) {
    log.info("Handling OrderCreatedEvent for orderId: {}", event.getOrderId());
    /// Handle the event OrderCreatedEvent and save it to the database.
    /// We can verify the event again here.
    orderService.createOrder(event);
  }

  @EventHandler
  public void on(OrderCreateRollbackedEvent event) {
    ///  Rollback the order creation in case of failure.
    log.info("Handling OrderCreateRollbackedEvent for orderId: {}", event.getOrderId());
    orderService.rollbackOrderCreated(event);
  }

  @EventHandler
  public void on(CreateOrderConfirmedEvent event) {
    /// Confirm the order creation.
    log.info("Handling CreateOrderConfirmedEvent for orderId: {}", event.getOrderId());
    orderService.confirmCreateOrder(event);
  }
}
