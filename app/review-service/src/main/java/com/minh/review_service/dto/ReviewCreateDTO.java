package com.minh.review_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateDTO {
  private String accountId;
  private String productId;
  private Integer rating;
  private String content;
  private List<String> reviewImages;
}
