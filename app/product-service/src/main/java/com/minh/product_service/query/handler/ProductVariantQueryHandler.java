package com.minh.product_service.query.handler;

import com.minh.product_service.query.queries.FindVariantQuery;
import com.minh.product_service.query.queries.FindVariantsByProductIdQuery;
import com.minh.product_service.query.queries.FindVariantsQuery;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVariantQueryHandler {
  private final ProductVariantService productVariantService;

  @QueryHandler
  public ResponseData handle(FindVariantQuery query) {
    return productVariantService.findVariant(query);
  }

  @QueryHandler
  public ResponseData handle(FindVariantsQuery query) {
    return productVariantService.findVariants(query);
  }

  @QueryHandler
  public ResponseData handle(FindVariantsByProductIdQuery query) {
    return productVariantService.findVariantsByProductId(query);
  }
}
