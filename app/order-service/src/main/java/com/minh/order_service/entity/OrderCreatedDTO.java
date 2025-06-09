package com.minh.order_service.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedDTO {
  private String accountId;
  private List<String> productVariantIds;
}
