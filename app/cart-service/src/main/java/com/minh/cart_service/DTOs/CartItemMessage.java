package com.minh.cart_service.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemMessage {
  private String cartItemId;
  private String productVariantId;
}
