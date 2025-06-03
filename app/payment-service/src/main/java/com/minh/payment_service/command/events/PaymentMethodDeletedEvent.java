package com.minh.payment_service.command.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDeletedEvent {
  private String paymentMethodId;
  private String errorMsg;
}
