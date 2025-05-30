package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
  @Id
  private String id;
  private String name;
  private String slug;
  private String categoryId;
  private String description;
  private String cover;
  private Double price;
  private Double originalPrice;
  private Long soldItems;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
  @Enumerated(value = EnumType.STRING)
  private ProductStatus status;
}