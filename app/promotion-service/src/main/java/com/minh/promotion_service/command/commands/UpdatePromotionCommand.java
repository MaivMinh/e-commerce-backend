package com.minh.promotion_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromotionCommand {
  @TargetAggregateIdentifier
  private String promotionId;
  private String code;
  private String type;
  private Double discountValue;
  private Double minOrderValue;
  private Timestamp startDate;
  private Timestamp endDate;
  private Integer usageLimit;
  private Integer usageCount;
  private String status;
}
