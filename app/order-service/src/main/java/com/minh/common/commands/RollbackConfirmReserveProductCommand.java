package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollbackConfirmReserveProductCommand {
  @TargetAggregateIdentifier
  private String reserveProductId;
  private String orderId;
  private String paymentId;
  private String orderPromotionId;
  private String errorMsg;
}
