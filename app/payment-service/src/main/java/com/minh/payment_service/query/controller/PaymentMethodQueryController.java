package com.minh.payment_service.query.controller;

import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsQuery;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsWithoutParamsQuery;
import com.minh.payment_service.query.queries.FindPaymentMethodsByTypeQuery;
import com.minh.payment_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/payment-methods")
public class PaymentMethodQueryController {
  private final QueryGateway queryGateway;

  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findAllPaymentMethods(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                                            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                                            @RequestParam(value = "isActive", required = false) Boolean isActive) {

    size = size < 1 ? 10 : size; // Ensure size is at least 1
    page = page <= 0 ? 0 : page - 1; // Ensure page is not negative

    FindAllPaymentMethodsQuery query = new FindAllPaymentMethodsQuery(search, filter, isActive, size, page);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping(value = "/all")
  public ResponseEntity<ResponseData> findAllPaymentMethods() {
    FindAllPaymentMethodsWithoutParamsQuery query = new FindAllPaymentMethodsWithoutParamsQuery();
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping(value = "/{type}")
  public ResponseEntity<ResponseData> findPaymentMethodsByType(@PathVariable String type) {
    FindPaymentMethodsByTypeQuery query = new FindPaymentMethodsByTypeQuery(type);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}