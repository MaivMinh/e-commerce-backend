package com.minh.product_service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantDTO {
  private String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;
}
