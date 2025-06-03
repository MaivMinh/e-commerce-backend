package com.minh.promotion_service.command.commands;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionCommand {
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
