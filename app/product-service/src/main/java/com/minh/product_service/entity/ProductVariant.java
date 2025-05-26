package com.minh.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant extends BaseEntity {
  @Id
  public String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;

}
