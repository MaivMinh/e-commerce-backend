package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_attributes_product_skus")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductAttributeProductSku extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productSkuId;
  private Long productAttributeId;
}