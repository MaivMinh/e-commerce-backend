package com.minh.payment_service.query.handler;

import com.minh.payment_service.query.queries.FindAllPaymentMethodsQuery;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsWithoutParamsQuery;
import com.minh.payment_service.query.queries.FindPaymentMethodsByTypeQuery;
import com.minh.payment_service.response.ResponseData;
import com.minh.payment_service.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMethodQueryHandler {
  private final PaymentMethodService paymentMethodService;
  @QueryHandler
  public ResponseData handle(FindAllPaymentMethodsQuery query) {
    return paymentMethodService.findAllPaymentMethods(query);
  }

  @QueryHandler
  public ResponseData handle(FindAllPaymentMethodsWithoutParamsQuery query) {
    return paymentMethodService.findAllPaymentMethodsWithoutParams(query);
  }

  @QueryHandler
  public ResponseData handle(FindPaymentMethodsByTypeQuery query) {
    return paymentMethodService.findPaymentMethodsByType(query);
  }
}
