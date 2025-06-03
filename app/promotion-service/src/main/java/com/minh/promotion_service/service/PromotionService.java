package com.minh.promotion_service.service;

import com.minh.promotion_service.command.events.PromotionCreatedEvent;
import com.minh.promotion_service.command.events.PromotionUpdatedEvent;
import com.minh.promotion_service.entity.Promotion;
import com.minh.promotion_service.entity.PromotionStatus;
import com.minh.promotion_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {
  private final PromotionRepository promotionRepository;

  public void createPromotion(PromotionCreatedEvent event) {
    // Convert the event to a promotion entity and save it to the repository.
    // Assuming you have a Promotion entity class that matches your database schema.
     Promotion promotion = new Promotion();
     promotion.setId(event.getPromotionId());
     promotion.setCode(event.getCode());
     promotion.setType(event.getType());
     promotion.setDiscountValue(event.getDiscountValue());
     promotion.setMinOrderValue(event.getMinOrderValue());
     promotion.setStartDate(event.getStartDate());
     promotion.setEndDate(event.getEndDate());
     promotion.setUsageLimit(event.getUsageLimit());
     promotion.setUsageCount(0);
     promotion.setStatus(PromotionStatus.active);
//     Save the promotion to the repository.
     promotionRepository.save(promotion);
  }

  public void updatePromotion(PromotionUpdatedEvent event) {
    // Find the existing promotion by ID and update its fields.
    Promotion promotion = promotionRepository.findById(event.getPromotionId())
            .orElseThrow(() -> new RuntimeException("Promotion not found"));

    promotion.setCode(event.getCode());
    promotion.setType(event.getType());
    promotion.setDiscountValue(event.getDiscountValue());
    promotion.setMinOrderValue(event.getMinOrderValue());
    promotion.setStartDate(event.getStartDate());
    promotion.setEndDate(event.getEndDate());
    promotion.setUsageLimit(event.getUsageLimit());
    promotion.setUsageCount(event.getUsageCount());
    promotion.setStatus(PromotionStatus.valueOf(event.getStatus()));

    // Save the updated promotion to the repository.
    promotionRepository.save(promotion);
  }
}
