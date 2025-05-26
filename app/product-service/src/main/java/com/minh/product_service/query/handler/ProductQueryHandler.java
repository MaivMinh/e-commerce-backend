package com.minh.product_service.query.handler;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.query.queries.FetchProductQuery;
import com.minh.product_service.query.queries.FindProductBySlugQuery;
import com.minh.product_service.query.queries.FindProductsQuery;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {
  private final ProductService productService;

  /// Done.
  @QueryHandler
  public ProductDTO handle(FetchProductQuery query) {
    return productService.fetchProduct(query.getId());
  }

  @QueryHandler
  public ProductDTO handle(FindProductBySlugQuery query) {
    return productService.findProductBySlug(query);
  }

  @QueryHandler
  public ResponseData handle(FindProductsQuery query) {
    return productService.findProducts(query);
  }
}
