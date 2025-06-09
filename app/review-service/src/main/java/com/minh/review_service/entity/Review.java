package com.minh.review_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "reviews")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
  @Id
  private String id;
  private String userId;
  private String productId;
  private Integer rating;
  private String content;
  private Integer likes;
}
