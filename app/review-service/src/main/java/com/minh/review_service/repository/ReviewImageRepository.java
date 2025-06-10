package com.minh.review_service.repository;

import com.minh.review_service.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, String> {
  List<ReviewImage> findAllByReviewId(String reviewId);
}
