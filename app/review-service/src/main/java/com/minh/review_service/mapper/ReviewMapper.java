package com.minh.review_service.mapper;

import com.minh.review_service.dto.ReviewDTO;
import com.minh.review_service.entity.Review;

public class ReviewMapper {
  public static void mapToReviewDTO(Review review, ReviewDTO reviewDTO) {
    if (review == null) {
      return;
    }
    if (reviewDTO == null) {
      reviewDTO = new ReviewDTO();
    }
    reviewDTO.setId(review.getId());
    reviewDTO.setAccountId(review.getAccountId());
    reviewDTO.setProductId(review.getProductId());
    reviewDTO.setRating(review.getRating());
    reviewDTO.setContent(review.getContent());
    reviewDTO.setLikes(review.getLikes());
  }
}
