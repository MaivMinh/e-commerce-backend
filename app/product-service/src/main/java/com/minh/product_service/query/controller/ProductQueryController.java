package com.minh.product_service.query.controller;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.dto.ProductFilterDTO;
import com.minh.product_service.query.queries.*;
import com.minh.product_service.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Phương thức lấy thông tin sản phẩm theo slug.
  @GetMapping(value = "/slug")
  public ResponseEntity<ResponseData> findProductBySlug(@RequestParam(value = "name", required = true) String name) {
    FindProductBySlugQuery query = FindProductBySlugQuery.builder()
            .slug(name)
            .build();

    /// Send query to Query Bus and wait for result.
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Phương thức lấy danh sách sản phẩm.
  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findProducts(@RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                                                   @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                   HttpServletRequest request) {
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

  /// Phương thức thực hiện filter.
  @PostMapping(value = "/filtered-products")
  public ResponseEntity<ResponseData> filterProducts(@RequestBody ProductFilterDTO productFilterDTO,
                                                     @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                                                     @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;

    FindProductsByFilterQuery query = FindProductsByFilterQuery.builder()
            .filter(productFilterDTO)
            .sort(sort)
            .page(page)
            .size(size)
            .build();


    /// Send query to Query Bus and wait for result.
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Phương thức lấy ra danh sách sản phẩm mới nhất.
  @GetMapping(value = "/newest-products")
  public ResponseEntity<ResponseData> findNewestProducts(@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                         @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
    size = (size > 0) ? size : 10;
    page = (page > 0) ? (page - 1) : 0;

    FindNewestProductsQuery query = FindNewestProductsQuery.builder()
            .size(size)
            .page(page)
            .build();

    /// Send query to Query Bus and wait for result.
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Phương thức tìm kiếm sản phẩm theo tên.
  @PostMapping("/search")
  public ResponseEntity<ResponseData> searchProducts(@RequestParam(value = "name", required = true) String name,
                                                     @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                                                     @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;

    SearchProductsQuery query = SearchProductsQuery.builder()
            .name(name)
            .sort(sort)
            .page(page)
            .size(size)
            .build();

    /// Send query to Query Bus and wait for result.
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}