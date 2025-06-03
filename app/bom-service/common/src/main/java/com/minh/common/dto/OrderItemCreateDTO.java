package com.minh.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateDTO {
  private String id;
  private String orderId;
  private String productVariantId;
  private Integer quantity;
  private Double price;
  private Double total;
}