package com.minh.common.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackReserveProductCommand {
  @TargetAggregateIdentifier
  private String orderId;
}
