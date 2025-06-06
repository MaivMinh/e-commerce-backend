package com.minh.promotion_service.query.projection;

import com.minh.promotion_service.command.events.PromotionCreatedEvent;
import com.minh.promotion_service.command.events.PromotionDeletedEvent;
import com.minh.promotion_service.command.events.PromotionUpdatedEvent;
import com.minh.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup(value = "promotion-group")
public class PromotionProjection {
  private final PromotionService promotionService;

  @EventHandler
  public void on(PromotionCreatedEvent event) {
    /// Lưu vào trong database.
    promotionService.createPromotion(event);
  }

  @EventHandler
  public void on(PromotionUpdatedEvent event) {
    /// Cập nhật vào trong database.
    promotionService.updatePromotion(event);
  }

  @EventHandler
  public void on(PromotionDeletedEvent event) {
    promotionService.deletePromotion(event);
  }
}
