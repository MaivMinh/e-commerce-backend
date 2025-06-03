package com.minh.payment_service.command.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodUpdatedEvent {
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
