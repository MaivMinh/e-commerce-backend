package com.minh.payment_service.query.controller;

import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsQuery;
import com.minh.payment_service.query.queries.FindPaymentMethodsByTypeQuery;
import com.minh.payment_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/payment-methods")
public class PaymentMethodQueryController {
  private final QueryGateway queryGateway;

  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findAllPaymentMethods() {
    FindAllPaymentMethodsQuery query = new FindAllPaymentMethodsQuery();
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