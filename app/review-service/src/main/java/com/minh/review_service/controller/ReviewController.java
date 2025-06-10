package com.minh.review_service.controller;

import ch.qos.logback.core.util.StringUtil;
import com.minh.review_service.dto.ReviewCreateDTO;
import com.minh.review_service.response.ResponseData;
import com.minh.review_service.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/reviews")
public class ReviewController {
  private final ReviewService reviewService;

  @PostMapping("")
  public ResponseEntity<ResponseData> createReview(@RequestBody @Valid ReviewCreateDTO reviewCreateDTO) {
    // Logic to handle the review creation
    ResponseData response = reviewService.createReview(reviewCreateDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping(value = "/{productId}")
  public ResponseEntity<ResponseData> getReviewsByProductId(@PathVariable String productId,
                                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                                            @RequestParam(required = false, defaultValue = "10") Integer size) {
    // Logic to retrieve reviews by product ID
    page = Math.max(page - 1, 0); // Ensure page is at least 1
    size = size > 0 ? size : 10; // Ensure size is at least 1

    ResponseData response = reviewService.getReviewsByProductId(productId, page, size);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @PostMapping(value = "/{reviewId}/like")
  public ResponseEntity<ResponseData> likeReview(@PathVariable String reviewId, HttpServletRequest request) {
    // Logic to handle liking a review
    if (!StringUtils.hasText("ACCOUNT-ID")) {
      return ResponseEntity.status(403).body(ResponseData.builder()
          .status(403)
          .message("Account ID is required")
          .build());
    }

    ResponseData response = reviewService.likeReview(reviewId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}