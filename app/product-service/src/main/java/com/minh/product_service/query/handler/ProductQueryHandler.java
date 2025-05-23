package com.minh.product_service.query.handler;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.query.queries.FetchProductQuery;
import com.minh.product_service.query.queries.FetchProductsByCriteriaQuery;
import com.minh.product_service.query.queries.FetchProductsQuery;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {
  private final ProductService productService;

  /// Done.
  @QueryHandler
  public ProductDTO handle(FetchProductQuery query) {
    return productService.fetchProduct(query.getId());
  }

  /// Done.
  @QueryHandler
  public ResponseData handle(FetchProductsQuery query) {
    return productService.fetchProducts(query.getPage(), query.getSize(), query.getSort());
  }

  /// Done.
  @QueryHandler
  public ResponseData handle(FetchProductsByCriteriaQuery query) {
    return productService.fetchProductsByCriteria(query.getCriteria(), query.getSort(), query.getPage(), query.getSize());
  }
}
