package com.minh.product_service.query.controller;

import com.minh.product_service.query.queries.FetchCategoriesQuery;
import com.minh.product_service.query.queries.FetchCategoryQuery;
import com.minh.product_service.query.queries.FindAllCategoriesQuery;
import com.minh.product_service.query.queries.SearchCategoriesQuery;
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
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories", produces = {"application/json"})
public class CategoryQueryController {
  private final QueryGateway queryGateway;

  /// Phương thức lấy danh sách tất cả các danh mục. (Có phân trang)
  /// DONE.
  @GetMapping(value = "")
  public ResponseEntity<ResponseData> fetchCategories(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                      @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                      @RequestParam(value = "sort", defaultValue = "", required = false) String sort) {

    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;
    FetchCategoriesQuery query = FetchCategoriesQuery.builder()
            .page(page)
            .size(size)
            .sort(sort)
            .build();

    /// dispatch query
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Phương thức lấy hết
  @GetMapping(value = "/all")
  public ResponseEntity<ResponseData> findAllCategories() {
    FindAllCategoriesQuery query = new FindAllCategoriesQuery();
    /// dispatch query
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Phương thức lấy một danh mục theo id.
  /// DONE.
  @GetMapping(value = "/{id}")
  public ResponseEntity<ResponseData> fetchCategory(@PathVariable(value = "id") String id) {
    FetchCategoryQuery query = FetchCategoryQuery.builder()
            .id(id)
            .build();

    /// dispatch query
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
