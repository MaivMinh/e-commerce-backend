package com.minh.common.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackApplyPromotionCommand {
  @TargetAggregateIdentifier
  private String orderId;
  private String promotionId;
}
