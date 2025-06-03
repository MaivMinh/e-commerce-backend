package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RollbackApplyPromotionCommand {
  @TargetAggregateIdentifier
  private String orderPromotionId;
  private String orderId;
  private String promotionId;
  private String errorMsg;
}
