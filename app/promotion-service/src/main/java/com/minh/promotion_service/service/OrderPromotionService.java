package com.minh.promotion_service.service;

import com.minh.common.events.CreateOrderConfirmedEvent;
import com.minh.common.events.PromotionAppliedEvent;
import com.minh.common.events.PromotionApplyRollbackedEvent;
import com.minh.promotion_service.entity.OrderPromotion;
import com.minh.promotion_service.entity.Promotion;
import com.minh.promotion_service.entity.PromotionStatus;
import com.minh.promotion_service.repository.OrderPromotionRepository;
import com.minh.promotion_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPromotionService {
  private final OrderPromotionRepository orderPromotionRepository;
  private final PromotionRepository promotionRepository;


  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void applyPromotion(PromotionAppliedEvent event) {
    log.info("Saving OrderPromotion for orderId: {}", event.getOrderId());
    String promotionId = event.getPromotionId();
    if (!StringUtils.hasText(promotionId)) {
      log.warn("Promotion ID is empty, skipping saving OrderPromotion for orderId: {}", event.getOrderId());
      return;
    }
    String orderId = event.getOrderId();
    Promotion promotion = promotionRepository.findById(promotionId)
            .orElseThrow(() -> new RuntimeException("Promotion not found with id: " + promotionId));

    if (promotion.getUsageCount() >= promotion.getUsageLimit() || promotion.getEndDate().before(Timestamp.valueOf(LocalDateTime.now())) || promotion.getStatus().equals(PromotionStatus.inactive)) {
      throw new RuntimeException("Promotion is not valid or has reached its usage limit.");
    }
    // Create a new order promotion record
    OrderPromotion orderPromotion = OrderPromotion.builder()
            .id(event.getOrderPromotionId())
            .orderId(orderId)
            .promotionId(promotionId)
            .isUsed(true)
            .usedAt(Timestamp.valueOf(LocalDateTime.now()))
            .build();

    // Save the order promotion record
    orderPromotionRepository.save(orderPromotion);
    log.info("Saved OrderPromotion for orderId: {}, promotionId: {}", orderId, promotionId);

    // Update the promotion usage count
    promotion.setUsageCount(promotion.getUsageCount() + 1);
    promotionRepository.save(promotion);
    log.info("Save promotion usage count for promotionId: {}", promotionId);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void rollbackApplyPromotion(PromotionApplyRollbackedEvent event) {
    /// Hàm thực hiện rollback lại việc áp dụng khuyến mãi cho đơn hàng
    String orderId = event.getOrderId();
    OrderPromotion orderPromotion = orderPromotionRepository.findByOrderId(orderId)
            .orElse(null);
    if (orderPromotion == null) {
      /// User didn't apply any promotion for this order
      log.warn("Promotion ID is empty, skipping saving OrderPromotion for orderId: {}", event.getOrderId());
      return;
    }
    Promotion promotion = promotionRepository.findById(orderPromotion.getPromotionId()).orElseThrow(
            () -> new RuntimeException("Promotion not found with id: " + orderPromotion.getPromotionId())
    );
    promotion.setUsageCount(promotion.getUsageCount() - 1);
    promotionRepository.save(promotion);
    /// Delete the order promotion record
    orderPromotionRepository.delete(orderPromotion);
    if (promotion.getUsageCount() < 0) {
      promotion.setUsageCount(0);
    }
  }
}
