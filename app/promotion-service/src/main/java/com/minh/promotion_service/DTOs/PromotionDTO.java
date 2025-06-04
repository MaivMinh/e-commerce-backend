package com.minh.promotion_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
  private String id;
  @NotEmpty(message = "Promotion code cannot be empty")
  private String code;
  @NotEmpty(message = "Promotion type cannot be empty")
  private String type;
  @NotNull(message = "Promotion name cannot be null")
  private Double discountValue;
  private Double minOrderValue;
  @NotNull(message = "Start date cannot be null")
  private Timestamp startDate;
  @NotNull(message = "End date cannot be null")
  private Timestamp endDate;
  @NotNull(message = "Usage limit cannot be null")
  private Integer usageLimit;
  private Integer usageCount;
  private String status;
}
