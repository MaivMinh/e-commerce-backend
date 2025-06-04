package com.minh.promotion_service.mapper;

import com.minh.promotion_service.DTOs.PromotionDTO;
import com.minh.promotion_service.entity.Promotion;

public class PromotionMapper {
  public static void mapToPromotionDTO(Promotion promotion, PromotionDTO promotionDTO) {
    if (promotion == null) {
      return;
    }
    if (promotionDTO == null) {
      promotionDTO = new PromotionDTO();
    }

    promotionDTO.setId(promotion.getId());
    promotionDTO.setCode(promotion.getCode());
    promotionDTO.setType(promotion.getType());
    promotionDTO.setDiscountValue(promotion.getDiscountValue());
    promotionDTO.setMinOrderValue(promotion.getMinOrderValue());
    promotionDTO.setStartDate(promotion.getStartDate());
    promotionDTO.setEndDate(promotion.getEndDate());
    promotionDTO.setUsageLimit(promotion.getUsageLimit());
    promotionDTO.setUsageCount(promotion.getUsageCount());
    promotionDTO.setStatus(promotion.getStatus().name());
  }
}
