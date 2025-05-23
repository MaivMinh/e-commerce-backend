package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductImage extends BaseEntity {
  @Id
  private String id;
  private Integer productId;
  private String url;
}