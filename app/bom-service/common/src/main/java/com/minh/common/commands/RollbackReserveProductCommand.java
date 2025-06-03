package com.minh.common.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackReserveProductCommand {
  @TargetAggregateIdentifier
  private String reserveProductId;
  private String orderId;
  private String errorMsg;
}
