package com.minh.review_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "review_images")
public class ReviewImage extends BaseEntity {
  @Id
  private String id;
  private String reviewId;
  private String url;
}
