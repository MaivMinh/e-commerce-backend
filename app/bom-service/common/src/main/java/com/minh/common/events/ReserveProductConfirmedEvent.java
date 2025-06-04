package com.minh.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveProductConfirmedEvent {
  private String reserveProductId;
  private String paymentId;
  private String orderPromotionId;
  private String orderId;
  private String errorMsg;
}
