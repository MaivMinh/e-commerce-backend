package com.minh.product_service.query.projection;

import com.minh.product_service.command.events.ProductVariantCreatedEvent;
import com.minh.product_service.command.events.ProductVariantUpdatedEvent;
import com.minh.product_service.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductVariantProjection {
  private final ProductVariantService productVariantService;

  @EventHandler
  public void on(ProductVariantCreatedEvent event) {
    /// Save product variant to database.
    productVariantService.createProductVariant(event);
  }

  @EventHandler
  public void on(ProductVariantUpdatedEvent event) {
    /// Update product variant in database.
    productVariantService.updateProductVariant(event);
  }

}
