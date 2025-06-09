package com.minh.cart_service.service;

import com.minh.cart_service.DTOs.CartItemDTO;
import com.minh.cart_service.DTOs.CartItemMessage;
import com.minh.cart_service.DTOs.ProductVariantDTO;
import com.minh.cart_service.grpc.ProductServiceGrpcClient;
import com.minh.cart_service.mapper.CartItemMapper;
import com.minh.cart_service.model.Cart;
import com.minh.cart_service.model.CartItem;
import com.minh.cart_service.repository.CartItemRepository;
import com.minh.cart_service.repository.CartRepository;
import com.minh.cart_service.response.ResponseData;
import com.minh.product_service_grpc.FindProductVariantsByIdsRequest;
import com.minh.product_service_grpc.FindProductVariantsByIdsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemService {
  private final CartItemRepository cartItemRepository;
  private final CartRepository cartRepository;
  private final ProductServiceGrpcClient productServiceGrpcClient;

  /// Hàm thực hiện tạo một mục trong giỏ hàng của người dùng
  public ResponseData createCartItem(String accountId, CartItemDTO cartItemDTO) {
    try {
      Cart saved = cartRepository.findByAccountId(accountId).orElseGet(() -> {
        Cart newCart = new Cart();
        newCart.setId(UUID.randomUUID().toString());
        newCart.setAccountId(accountId);
        return cartRepository.save(newCart);
      });

      CartItem existingItem = cartItemRepository.checkHasTheSameProductVariantInCartId(saved.getId(), cartItemDTO.getProductVariantDTO().getId()).orElse(null);

      if (existingItem != null) {
        existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
        cartItemRepository.save(existingItem);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .message("Cart item updated successfully")
                .data(null)
                .build();
      }

      // If no existing item, create a new cart item
      CartItem cartItem = new CartItem();
      cartItem.setId(UUID.randomUUID().toString());
      cartItem.setCartId(saved.getId());
      cartItem.setQuantity(cartItemDTO.getQuantity());
      cartItem.setProductVariantId(cartItemDTO.getProductVariantDTO().getId());
      cartItemRepository.save(cartItem);
      return ResponseData.builder()
              .status(HttpStatus.CREATED.value())
              .message("Success")
              .data(null)
              .build();
    } catch (Exception e) {
      log.error("Error creating cart item: {}", e.getMessage());
      throw new RuntimeException("Error creating cart item: " + e.getMessage());
    }
  }

  public ResponseData updateCartItem(CartItemDTO cartItemDTO) {
    try {
      CartItem item = cartItemRepository.findById(cartItemDTO.getId())
              .orElseThrow(() -> new RuntimeException("Cart item not found"));

      item.setQuantity(cartItemDTO.getQuantity());
      item.setProductVariantId(cartItemDTO.getProductVariantDTO().getId());
      cartItemRepository.save(item);
      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Success")
              .data(null)
              .build();
    } catch (Exception e) {
      log.error("Error updating cart item: {}", e.getMessage());
      throw new RuntimeException("Error updating cart item: " + e.getMessage());
    }
  }

  public ResponseData deleteCartItem(String id) {
    try {
      CartItem item = cartItemRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Cart item not found"));
      cartItemRepository.delete(item);
      return ResponseData.builder()
              .status(HttpStatus.NO_CONTENT.value())
              .message("Success")
              .data(null)
              .build();
    } catch (Exception e) {
      log.error("Error deleting cart item: {}", e.getMessage());
      throw new RuntimeException("Error deleting cart item: " + e.getMessage());
    }
  }

  public ResponseData findCartByAccountId(String accountId) {
    try {
      Cart cart = cartRepository.findByAccountId(accountId).orElse(null);
      if (cart == null) {
        return ResponseData.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Cart not found for account ID: " + accountId)
                .data(null)
                .build();
      }

      List<CartItemMessage> messages = cartItemRepository.findAllCartItemIdsByCartId(cart.getId());

      FindProductVariantsByIdsRequest request = FindProductVariantsByIdsRequest.newBuilder()
              .addAllCartItems(messages.stream().map(message -> com.minh.product_service_grpc.CartItemMessageGrpc.newBuilder()
                      .setCartItemId(message.getCartItemId())
                      .setProductVariantId(message.getProductVariantId())
                      .build()
              ).collect(Collectors.toList()))
              .build();

      FindProductVariantsByIdsResponse response = productServiceGrpcClient.findProductVariantsByIds(request);
      if (response.getStatus() != HttpStatus.OK.value()) {
        return ResponseData.builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .data(null)
                .build();
      }

      List<CartItemDTO> cartItemDTOs = response.getProductVariantsList().stream().map(productVariant -> {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(productVariant.getCartItemId());
        cartItemDTO.setCartId(cart.getId());
        cartItemDTO.setQuantity(cartItemRepository.findQuantityById(cartItemDTO.getId()));
        cartItemDTO.setProductVariantDTO(
                ProductVariantDTO.builder()
                        .id(productVariant.getProductVariantId())
                        .name(productVariant.getProductName())
                        .slug(productVariant.getProductSlug())
                        .price(productVariant.getProductVariantPrice())
                        .originalPrice(productVariant.getProductVariantOriginalPrice())
                        .cover(productVariant.getProductCover())
                        .size(productVariant.getProductVariantSize())
                        .colorName(productVariant.getProductVariantColorName())
                        .colorHex(productVariant.getProductVariantColorHex())
                        .build()
        );
        return cartItemDTO;
      }).collect(Collectors.toList());

      double subTotal = 0;
      for (CartItemDTO item : cartItemDTOs) {
        subTotal += item.getProductVariantDTO().getPrice() * item.getQuantity();
      }
      Map<String, Object> map = new HashMap<>();
      map.put("items", cartItemDTOs);
      map.put("subTotal", subTotal);

      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Success")
              .data(map)
              .build();
    } catch (Exception e) {
      log.error("Error finding cart by account ID: {}", e.getMessage());
      throw new RuntimeException("Error finding cart by account ID: " + e.getMessage());
    }
  }
}