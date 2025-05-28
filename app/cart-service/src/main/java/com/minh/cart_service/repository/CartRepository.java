package com.minh.cart_service.repository;

import com.minh.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
  Optional<Cart> findByAccountId(String accountId);
}
