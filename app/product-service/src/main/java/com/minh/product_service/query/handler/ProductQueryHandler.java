package com.minh.product_service.query.handler;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.query.queries.*;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {
  private final ProductService productService;

  /// Done.
  @QueryHandler
  public ResponseData handle(FetchProductQuery query) {
    return productService.fetchProduct(query.getId());
  }

  @QueryHandler
  public ResponseData handle(FindProductBySlugQuery query) {
    return productService.findProductBySlug(query);
  }

  @QueryHandler
  public ResponseData handle(FindProductsQuery query) {
    return productService.findProducts(query);
  }

  @QueryHandler
  public ResponseData handle(FindProductsByFilterQuery query) {
    return productService.findProductsByFilter(query);
  }

  @QueryHandler
  public ResponseData handle(FindNewestProductsQuery query) {
    return productService.findNewestProducts(query);
  }

  @QueryHandler
  public ResponseData handle(SearchProductsQuery query) {
    return productService.searchProductsByName(query);
  }
}