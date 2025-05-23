package com.minh.product_service.query.controller;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.query.queries.FetchProductQuery;
import com.minh.product_service.query.queries.FetchProductsByCriteriaQuery;
import com.minh.product_service.query.queries.FetchProductsQuery;
import com.minh.product_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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


  /// Phương thức lấy ra danh sách sản phẩm.
  /// Done!!!
  @GetMapping(value = "")
  public ResponseEntity<ResponseData> fetchProducts(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                    @RequestParam(value = "sort", defaultValue = "", required = false) String sort) {
    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;
    FetchProductsQuery query = FetchProductsQuery.builder()
            .page(page)
            .size(size)
            .sort(sort)
            .build();

    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Phương thức thực hiện tìm kiểm sản phẩm theo các tiêu chí.
  /// Done.
  @PostMapping(value = "/search")
  public ResponseEntity<ResponseData> fetchProductsByCriteria(@RequestBody Map<String, String> criteria,
                                                              @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
                                                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    /**
       POST /search?sort=""&page=?&size=?
       {
         name: String,
         description: String,
       }
     */

    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;
    if (criteria == null) {
      criteria = new HashMap<String, String>();
    }
    FetchProductsByCriteriaQuery query = FetchProductsByCriteriaQuery.builder()
            .criteria(criteria)
            .page(page)
            .size(size)
            .sort(sort)
            .build();

    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  
}
