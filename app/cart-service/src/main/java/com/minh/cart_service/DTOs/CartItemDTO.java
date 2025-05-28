package com.minh.cart_service.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
  private String id;
  private String cartId;
  private ProductVariantDTO productVariantDTO;
  @NotNull(message = "Product ID cannot be empty")
  private Integer quantity;
}
