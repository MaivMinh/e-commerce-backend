package com.minh.review_service.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
  private String id;
  private String accountId;
  private String productId;
  private Integer rating;
  private String content;
  private Integer likes;
  private List<ReviewImageDTO> reviewImageDTOs;
}
