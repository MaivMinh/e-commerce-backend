package com.minh.review_service.service;

import com.minh.review_service.dto.ReviewCreateDTO;
import com.minh.review_service.dto.ReviewDTO;
import com.minh.review_service.dto.ReviewImageDTO;
import com.minh.review_service.entity.AccountPurchase;
import com.minh.review_service.entity.Review;
import com.minh.review_service.entity.ReviewImage;
import com.minh.review_service.mapper.ReviewMapper;
import com.minh.review_service.repository.AccountPurchaseRepository;
import com.minh.review_service.repository.ReviewImageRepository;
import com.minh.review_service.repository.ReviewRepository;
import com.minh.review_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final AccountPurchaseRepository accountPurchaseRepository;
  private final ReviewImageRepository reviewImageRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public ResponseData createReview(ReviewCreateDTO reviewCreateDTO) {
    /// Kiểm tra xem người dùng này đã mua sản phẩm này chưa
    log.info("Find account purchase for account ID: {} and product ID: {}", reviewCreateDTO.getAccountId(), reviewCreateDTO.getProductId());
    List<AccountPurchase> accountPurchase = accountPurchaseRepository.findByAccountIdAndProductId(reviewCreateDTO.getAccountId(), reviewCreateDTO.getProductId());
    if (accountPurchase.isEmpty()) {
      log.warn("Account ID: {} has not purchased product ID: {}", reviewCreateDTO.getAccountId(), reviewCreateDTO.getProductId());
      return ResponseData.builder()
              .status(403)
              .message("You must purchase the product before reviewing it.")
              .build();
    }
    log.info("This account {} can review product {}", reviewCreateDTO.getAccountId(), reviewCreateDTO.getProductId());

    /// Tạo mới review.
    Review review = new Review();
    review.setId(UUID.randomUUID().toString());
    review.setAccountId(reviewCreateDTO.getAccountId());
    review.setProductId(reviewCreateDTO.getProductId());
    review.setRating(reviewCreateDTO.getRating());
    review.setContent(reviewCreateDTO.getContent());
    review.setLikes(0);
    reviewRepository.save(review);

    /// Tạo mới các review images.
    reviewCreateDTO.getReviewImages().forEach(imageUrl -> {
      ReviewImage reviewImage = new ReviewImage();
      reviewImage.setId(UUID.randomUUID().toString());
      reviewImage.setReviewId(review.getId());
      reviewImage.setUrl(imageUrl != null ? imageUrl : "");
      reviewImageRepository.save(reviewImage);
    });

    /// Trả về response thành công.
    return ResponseData.builder()
            .status(201)
            .message("Review created successfully")
            .build();
  }

  public ResponseData getReviewsByProductId(String productId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageable);

    if (reviews.isEmpty()) {
      return ResponseData.builder()
              .status(200)
              .message("No reviews found for product ID: " + productId)
              .build();
    }

    List<ReviewDTO> reviewDTOs = reviews.stream()
            .map(review -> {
              ReviewDTO dto = new ReviewDTO();
              ReviewMapper.mapToReviewDTO(review, dto);

              // Fetch review images
              List<ReviewImage> reviewImages = reviewImageRepository.findAllByReviewId((review.getId()));
              List<ReviewImageDTO> reviewImageDTOs = reviewImages.stream().map(imageUrl -> {
                ReviewImageDTO reviewImageDTO = new ReviewImageDTO();
                reviewImageDTO.setId(imageUrl.getId());
                reviewImageDTO.setUrl(imageUrl.getUrl());
                return reviewImageDTO;
              }).toList();
              dto.setReviewImageDTOs(reviewImageDTOs);
              return dto;
            })
            .toList();


    Map<String, Object> data = new HashMap<>();
    data.put("reviews", reviewDTOs);
    data.put("totalPages", reviews.getTotalPages());
    data.put("totalElements", reviews.getTotalElements());
    data.put("currentPage", reviews.getNumber() + 1);
    data.put("pageSize", reviews.getSize());

    return ResponseData.builder()
            .status(200)
            .data(data)
            .message("Reviews retrieved successfully")
            .build();
  }

  public ResponseData likeReview(String reviewId) {
    Optional<Review> optionalReview = reviewRepository.findById(reviewId);
    if (optionalReview.isEmpty()) {
      return ResponseData.builder()
              .status(404)
              .message("Review not found")
              .build();
    }

    Review review = optionalReview.get();
    review.setLikes(review.getLikes() + 1);
    reviewRepository.save(review);

    return ResponseData.builder()
            .status(200)
            .message("Review liked successfully")
            .build();
  }
}
