package com.minh.promotion_service.command.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletePromotionCommand {
  private String promotionId;
  private String errorMsg;
}
