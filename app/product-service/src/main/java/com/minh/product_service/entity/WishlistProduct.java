package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "wishlist_products")
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WishlistProduct extends BaseEntity {
  @Id
  private String id;
  private Long wishlistId;
  private Long productId;
}