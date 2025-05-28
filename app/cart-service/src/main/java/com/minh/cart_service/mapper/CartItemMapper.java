package com.minh.cart_service.mapper;


import com.minh.cart_service.DTOs.CartItemDTO;
import com.minh.cart_service.model.CartItem;

public class CartItemMapper {
  public static void mapCartItemDTOToCartItem(CartItemDTO cartItemDTO, CartItem cartItem) {
    cartItem.setProductVariantId(cartItemDTO.getProductVariantDTO().getId());
    cartItem.setQuantity(cartItemDTO.getQuantity());
  }

  public static void mapCartItemToCartItemDTO(CartItem item, CartItemDTO cartItemDTO) {
    cartItemDTO.setId(item.getId());
    //cartItemDTO.setProductVariantId(item.getProductVariantId());
    cartItemDTO.setQuantity(item.getQuantity());
  }
}
