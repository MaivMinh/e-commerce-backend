package com.minh.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionAppliedEvent {
  private String orderPromotionId;
  private String reserveProductId;
  private String orderId;
  private String promotionId;
  private String paymentMethodId;
  private Double amount;
  private String currency;
  private String errorMsg;
}
