package com.minh.payment_service.query.projection;

import com.minh.payment_service.command.events.PaymentMethodCreatedEvent;
import com.minh.payment_service.command.events.PaymentMethodDeletedEvent;
import com.minh.payment_service.command.events.PaymentMethodUpdatedEvent;
import com.minh.payment_service.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup(value = "payment-group")
public class PaymentMethodProjection {
  private final PaymentMethodService paymentMethodService;

  @EventHandler
  public void on(PaymentMethodCreatedEvent event) {
    // Handle the event and update the read model
    paymentMethodService.createPaymentMethod(event);
  }

  @EventHandler
  public void on(PaymentMethodUpdatedEvent event) {
    // Handle the event and update the read model
    paymentMethodService.updatePaymentMethod(event);
  }

  @EventHandler
  public void on(PaymentMethodDeletedEvent event) {
    // Handle the event and update the read model
    paymentMethodService.deletePaymentMethod(event);
  }
}
