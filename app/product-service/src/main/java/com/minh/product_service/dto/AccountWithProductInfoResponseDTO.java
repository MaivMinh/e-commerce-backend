package com.minh.product_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountWithProductInfoResponseDTO {
  private String accountId;
  private String productVariantId;
  private String productId;
}
