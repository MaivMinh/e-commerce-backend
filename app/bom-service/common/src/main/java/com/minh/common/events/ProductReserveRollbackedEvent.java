package com.minh.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReserveRollbackedEvent {
  private String reserveProductId;
  private String orderId;
  private String errorMsg;
}
