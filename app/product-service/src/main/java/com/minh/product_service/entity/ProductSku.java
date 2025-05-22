package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ProductSku class represents a stock keeping unit (SKU) for a product.
 */
@Entity
@Table(name = "product_skus")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductSku extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Long sizeAttributeId;
  private Long colorAttributeId;
  private Double price;
  private Integer quantity;
}
