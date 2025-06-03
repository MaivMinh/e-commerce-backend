package com.minh.payment_service.command.events;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentMethodCreatedEvent {
  private String paymentMethodId;
  private String code;
  private String name;
  private String description;
  private String type;
  private String provider;
  private String currency;
  private Boolean isActive;
  private String iconUrl;
}
