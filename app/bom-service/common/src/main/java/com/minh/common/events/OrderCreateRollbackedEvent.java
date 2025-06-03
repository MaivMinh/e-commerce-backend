package com.minh.common.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRollbackedEvent {
  private String orderId;
  private String errorMsg;
}
