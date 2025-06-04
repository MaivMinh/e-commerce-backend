package com.minh.product_service.query.projection;

import com.minh.common.events.ProductReserveRollbackedEvent;
import com.minh.common.events.ProductReservedEvent;
import com.minh.common.events.ReserveProductConfirmedEvent;
import com.minh.product_service.service.ReserveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
@Slf4j
public class ReserveProductProjection {
  private final ReserveProductService reserveProductService;

  @EventHandler
  public void on(ProductReservedEvent event) {
    log.info("Handling ProductReservedEvent for orderId: {}", event.getOrderId());
    reserveProductService.reserveProduct(event);
  }

  @EventHandler
  public void on(ProductReserveRollbackedEvent event) {
    log.info("Handling ProductReserveRollbackedEvent for orderId: {}", event.getOrderId());
    reserveProductService.rollbackReserveProduct(event);
  }

  @EventHandler
  public void on(ReserveProductConfirmedEvent event) {
    log.info("Handling ReserveProductConfirmedEvent for orderId: {}", event.getOrderId());
    reserveProductService.confirmReserveProduct(event);
  }
}
