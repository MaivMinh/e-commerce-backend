package com.minh.cart_service.DTOs;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDTO {
  private List<CartItemDTO> cartItems;
  private Double subTotal;
}
