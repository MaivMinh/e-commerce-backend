package com.minh.product_service.query.controller;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.query.queries.FetchProductQuery;
import com.minh.product_service.query.queries.FindProductBySlugQuery;
import com.minh.product_service.query.queries.FindProductsQuery;
import com.minh.product_service.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products", produces = {"application/json"})
@RequiredArgsConstructor
@Validated
public class ProductQueryController {
  private final QueryGateway queryGateway;

  /// Phương thức lấy ra sản phẩm cụ thể.
  /// Done!!!
  @GetMapping(value = "/{productId}")
  public ResponseEntity<ResponseData> fetchProduct(@PathVariable(value = "productId") String productId) {
    FetchProductQuery query = FetchProductQuery.builder()
            .id(productId)
            .build();
    /// Send query to Query Bus and wait for result.
    ProductDTO result = queryGateway.query(query, ResponseTypes.instanceOf(ProductDTO.class)).join();
    return ResponseEntity.ok(new ResponseData(200, "Success", result));
  }

  /// Phương thức lấy thông tin sản phẩm theo slug.
  @GetMapping(value = "/slug")
  public ResponseEntity<ResponseData> findProductBySlug(@RequestParam(value = "name", required = true) String name) {
    FindProductBySlugQuery query = FindProductBySlugQuery.builder()
            .slug(name)
            .build();

    /// Send query to Query Bus and wait for result.
    ProductDTO result = queryGateway.query(query, ResponseTypes.instanceOf(ProductDTO.class)).join();
    return ResponseEntity.status(HttpStatus.OK.value()).body(new ResponseData(200, "Success", result));
  }

  /// Phương thức lấy danh sách sản phẩm.
  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findProducts(@RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                                                   @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                   HttpServletRequest request) {
    System.out.println(request.getHeader("ACCOUNT-ID"));
    System.out.println(request.getHeader("ROLE"));
    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;
    FindProductsQuery query = FindProductsQuery.builder()
            .sort(sort)
            .page(page)
            .size(size)
            .build();

    /// Send query to Query Bus and wait for result.
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}