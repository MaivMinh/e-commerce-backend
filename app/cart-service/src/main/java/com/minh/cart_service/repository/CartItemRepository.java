package com.minh.cart_service.repository;

import com.minh.cart_service.DTOs.CartItemMessage;
import com.minh.cart_service.model.CartItem;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
  List<CartItem> findAllCartItemsByCartId(String id);

  List<CartItem> findAllByCartId(String id);

  @Query(value = "select cts.id as cartItemId, cts.product_variant_id as productVariantId from cart_items cts where cts.cart_id = :cartId", nativeQuery = true)
  List<CartItemMessage> findAllCartItemIdsByCartId(String cartId);

  @Query(value = "select quantity from cart_items where id = :id", nativeQuery = true)
  @NotNull(message = "Product ID cannot be empty") Integer findQuantityById(String id);

  @Query(value = "select * from cart_items where cart_id = :cartId and product_variant_id = :productVariantId", nativeQuery = true)
  Optional<CartItem> checkHasTheSameProductVariantInCartId(String cartId, String productVariantId);
}
