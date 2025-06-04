package com.minh.payment_service.query.projection;

import com.minh.common.events.PaymentProcessedEvent;
import com.minh.common.events.ProcessPaymentRollbackedEvent;
import com.minh.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("payment-group")
public class PaymentProjection {
  private final PaymentService paymentService;

  @EventHandler
  public void on(PaymentProcessedEvent event) {
    paymentService.processPayment(event);
  }

  @EventHandler
  public void on(ProcessPaymentRollbackedEvent event) {
    paymentService.rollbackProcessPayment(event);
  }
}
