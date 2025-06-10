package com.minh.product_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountWithProductInfoRequestDTO {
  private String accountId;
  private String productVariantId;
}
