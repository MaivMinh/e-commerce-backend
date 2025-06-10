package com.minh.review_service.repository;

import com.minh.review_service.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
  Page<Review> findAllById(String id, Pageable pageable);
  Page<Review> findAllByProductId(String productId, Pageable pageable);
}
