package com.minh.order_service.query.controller;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.minh.order_service.query.queries.FindAllOrdersQuery;
import com.minh.order_service.query.queries.FindOrdersOfAccountQuery;
import com.minh.order_service.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class OrderQueryController {
  private final QueryGateway queryGateway;

  @GetMapping(value = "/all")
  public ResponseEntity<ResponseData> findAllOrders(
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    page = page < 1 ? 0 : page - 1; // Convert to zero-based index
    size = size < 1 ? 10 : size; // Default to 10 if size is less than 1
    FindAllOrdersQuery query = new FindAllOrdersQuery(page, size);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findOrdersOfAccount(
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
          HttpServletRequest request) {

    String accountId = request.getHeader("ACCOUNT-ID");
    if (!StringUtils.hasText(accountId)) {
      return ResponseEntity.badRequest()
              .body(ResponseData.builder()
                      .status(401)
                      .message("Unauthorized: ACCOUNT-ID header is missing")
                      .build());
    }


    page = page < 1 ? 0 : page - 1; // Convert to zero-based index
    size = size < 1 ? 10 : size; // Default to 10 if size is less than 1
    FindOrdersOfAccountQuery query = new FindOrdersOfAccountQuery(page, size, accountId);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}