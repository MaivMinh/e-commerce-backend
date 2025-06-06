package com.minh.promotion_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePromotionCommand {
  @TargetAggregateIdentifier
  private String promotionId;
  private String errorMsg;
}
