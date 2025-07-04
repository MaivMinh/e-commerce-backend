package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmCreateOrderCommand {
  @TargetAggregateIdentifier
  private String orderId;
  private String paymentId;
  private String orderPromotionId;
  private String reserveProductId;
  private String errorMsg;
}
