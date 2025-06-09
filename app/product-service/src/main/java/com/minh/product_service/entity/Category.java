package com.minh.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
  @Id
  private String id;
  private String parentId;
  private String name;
  private String description;
  private String slug;
  private String image;
}