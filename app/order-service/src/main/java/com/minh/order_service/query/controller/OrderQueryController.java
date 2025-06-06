package com.minh.order_service.query.controller;

import com.minh.order_service.query.queries.FindAllOrdersQuery;
import com.minh.order_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class OrderQueryController {
  private final QueryGateway queryGateway;

  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findAllOrders(
      @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
      @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    page = page < 1 ? 0 : page - 1; // Convert to zero-based index
    size = size < 1 ? 10 : size; // Default to 10 if size is less than 1
    FindAllOrdersQuery query = new FindAllOrdersQuery(page, size);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

}
