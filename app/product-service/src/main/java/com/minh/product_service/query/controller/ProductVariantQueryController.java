package com.minh.product_service.query.controller;

import com.minh.product_service.query.queries.FindVariantQuery;
import com.minh.product_service.query.queries.FindVariantsByProductIdQuery;
import com.minh.product_service.query.queries.FindVariantsQuery;
import com.minh.product_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products", produces = {"application/json"})
@RequiredArgsConstructor
@Validated
public class ProductVariantQueryController {
  private final QueryGateway queryGateway;

  /// Hàm này dùng để lấy tất cả các biến thể sản phẩm với phân trang và sắp xếp.
  /// DONE!!!
  @GetMapping(value = "/variants")
  public ResponseEntity<ResponseData> findAllVariants(@RequestParam(value = "size", required = false) Integer size,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "sort", required = false) String sort) {
    size = (size != null) ? size : 10; // Default size
    page = (page != null) ? page : 0; // Default page
    FindVariantsQuery query = FindVariantsQuery.builder()
            .size(size)
            .page(page)
            .sort(sort)
            .build();

    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Hàm này dùng để lấy một biến thể sản phẩm theo ID.
  /// DONE!!!
  @GetMapping(value = "/variants/{variantId}")
  public ResponseEntity<ResponseData> findVariant(@PathVariable String variantId) {
    FindVariantQuery query = FindVariantQuery.builder()
            .variantId(variantId)
            .build();
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Hàm lấy tất cả các biến thể sản phẩm theo ID sản phẩm.
  /// DONE!!!
  @GetMapping(value = "/{productId}/variants")
  public ResponseEntity<ResponseData> findVariants(@PathVariable String productId) {
    FindVariantsByProductIdQuery query = FindVariantsByProductIdQuery.builder()
            .productId(productId)
            .build();
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
