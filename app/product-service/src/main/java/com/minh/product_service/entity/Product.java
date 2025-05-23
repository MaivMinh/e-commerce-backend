package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Product extends BaseEntity {
  @Id
  private String id;
  private String name;
  private String description;
  private String cover;
}