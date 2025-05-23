package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wishlists")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Wishlist extends BaseEntity {
  @Id
  private String id;
  private Long userId;
}