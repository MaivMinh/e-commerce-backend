package com.minh.product_service.query.projection;

import com.minh.product_service.command.event.ProductCreatedEvent;
import com.minh.product_service.command.event.ProductDeletedEvent;
import com.minh.product_service.command.event.ProductUpdatedEvent;
import com.minh.product_service.entity.Product;
import com.minh.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductProjection {
  private final ProductService productService;

  @EventHandler
  public void on(ProductCreatedEvent event) {
    Product product = new Product();
    BeanUtils.copyProperties(event, product);
    productService.createProduct(product);
  }

  @EventHandler
  public void on(ProductUpdatedEvent event) {
    Product product = new Product();
    BeanUtils.copyProperties(event, product);
    productService.updateProduct(product);
  }

  @EventSourcingHandler
  public void on(ProductDeletedEvent event) {
    Product product = new Product();
    BeanUtils.copyProperties(event, product);
    productService.deleteProduct(product);
  }
}
