package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentCommand {
  @TargetAggregateIdentifier
  private String paymentId;
  private String orderId;
  private String paymentMethodId;
  private Double amount;
  private String status;
  private String transactionId;
  private String currency;
  private String errorMsg;
}
