package com.minh.promotion_service.query.projection;

import com.minh.common.events.CreateOrderConfirmedEvent;
import com.minh.common.events.PromotionAppliedEvent;
import com.minh.common.events.PromotionApplyRollbackedEvent;
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
  public void on(PromotionAppliedEvent event) {
    orderPromotionService.applyPromotion(event);
  }

  @EventHandler
  public void on(PromotionApplyRollbackedEvent event) {
    orderPromotionService.rollbackApplyPromotion(event);
  }
}
