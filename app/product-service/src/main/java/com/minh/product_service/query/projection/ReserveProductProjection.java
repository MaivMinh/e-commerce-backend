package com.minh.product_service.query.projection;

import com.minh.common.events.ProductReservedEvent;
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
    log.info("Handling ProductReservedEvent for reserveProductId: {}", event.getReserveProductId());
    reserveProductService.reserveProduct(event.getOrderId(), event.getReserveProductItems());
  }
}
