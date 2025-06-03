package com.minh.payment_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentMethodCommand {
  @TargetAggregateIdentifier
  private String paymentMethodId;
  private String code;
  private String name;
  private String description;
  private String type;
  private String provider;
  private String currency;
  private Boolean isActive;
  private String iconUrl;
  private String errorMsg;
}
