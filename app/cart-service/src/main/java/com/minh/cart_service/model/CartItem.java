package com.minh.cart_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@ToString
public class CartItem extends BaseEntity {
  @Id
  private String id;
  private String cartId;
  private String productVariantId;
  private int quantity;
}
