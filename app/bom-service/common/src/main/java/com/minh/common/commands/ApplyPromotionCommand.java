package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyPromotionCommand {
  @TargetAggregateIdentifier
  private String orderPromotionId;
  private String orderId;
  private String promotionId;
  private String paymentMethodId;
  private Double amount;
  private String currency;
  private String errorMsg;
}
