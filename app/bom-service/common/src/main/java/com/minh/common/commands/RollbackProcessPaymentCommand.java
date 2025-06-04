package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollbackProcessPaymentCommand {
  @TargetAggregateIdentifier
  private String paymentId;
  private String orderPromotionId;
  private String reserveProductId;
  private String orderId;
  private String errorMsg;
}
