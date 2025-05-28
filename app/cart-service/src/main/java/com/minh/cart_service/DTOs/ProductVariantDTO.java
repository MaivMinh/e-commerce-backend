package com.minh.cart_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {
  @NotEmpty(message = "ID cannot be empty")
  private String id;
  private String name;
  private String slug;
  private String size;
  private String colorName;
  private String colorHex;
  private String cover;
  private Double price;
  private Double originalPrice;
}
