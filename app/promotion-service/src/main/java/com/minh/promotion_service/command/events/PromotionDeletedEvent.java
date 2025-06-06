package com.minh.promotion_service.command.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDeletedEvent {
  private String promotionId;
  private String errorMsg;
}
