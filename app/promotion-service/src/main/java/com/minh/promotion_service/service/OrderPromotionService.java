package com.minh.promotion_service.service;

import com.minh.common.events.PromotionAppliedEvent;
import com.minh.promotion_service.entity.OrderPromotion;
import com.minh.promotion_service.entity.Promotion;
import com.minh.promotion_service.entity.PromotionStatus;
import com.minh.promotion_service.repository.OrderPromotionRepository;
import com.minh.promotion_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderPromotionService {
  private final OrderPromotionRepository orderPromotionRepository;
  private final PromotionRepository promotionRepository;


  public void applyPromotion(PromotionAppliedEvent event) {
    String promotionId = event.getPromotionId();
    String orderId = event.getOrderId();
    Promotion promotion = promotionRepository.findById(promotionId)
            .orElseThrow(() -> new RuntimeException("Promotion not found with id: " + promotionId));

    if (promotion.getUsageCount() >= promotion.getUsageLimit() || promotion.getEndDate().before(Timestamp.valueOf(LocalDateTime.now())) || promotion.getStatus().equals(PromotionStatus.inactive)) {
      throw new RuntimeException("Promotion is not valid or has reached its usage limit.");
    }
    // Create a new order promotion record
    OrderPromotion orderPromotion = OrderPromotion.builder()
            .id(UUID.randomUUID().toString())
            .orderId(orderId)
            .promotionId(promotionId)
            .isUsed(true)
            .usedAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

    // Save the order promotion record
    orderPromotionRepository.save(orderPromotion);
  }
}
