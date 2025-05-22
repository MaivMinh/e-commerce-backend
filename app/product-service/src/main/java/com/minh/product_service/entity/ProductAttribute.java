package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ProductAttribute class represents the attributes of Product.
 */
@Entity
@Table(name = "product_attributes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductAttribute extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Type type;
  private String value;
}