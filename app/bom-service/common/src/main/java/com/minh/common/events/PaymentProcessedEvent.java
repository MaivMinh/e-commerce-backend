package com.minh.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
  private String paymentId;
  private String orderPromotionId;
  private String reserveProductId;
  private String orderId;
  private String paymentMethodId;
  private Double amount;
  private String status;
  private String transactionId;
  private String currency;
  private String errorMsg;

}
