package com.minh.promotion_service.query.projection;

import com.minh.common.events.PromotionAppliedEvent;
import com.minh.promotion_service.service.OrderPromotionService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("promotion-group")
public class OrderPromotionProjection {
  private final OrderPromotionService orderPromotionService;

  @EventHandler
  public void handle(PromotionAppliedEvent event) {
    orderPromotionService.applyPromotion(event);
  }
}
