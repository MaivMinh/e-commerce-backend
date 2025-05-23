package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "promotion_product_mappings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PromotionProductMapping extends BaseEntity {
  @Id
  private String id;
  private Long promotionId;
  private Long productId;
}