package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackCreateOrderCommand {
  @TargetAggregateIdentifier
  private String orderId;
  private String errorMsg;
}
