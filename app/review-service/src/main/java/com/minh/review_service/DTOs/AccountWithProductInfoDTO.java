package com.minh.review_service.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountWithProductInfoDTO {
  private String accountId;
  private String productVariantId;
}
