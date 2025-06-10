package com.minh.review_service.DTOs;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedMessageDTO {
  private String orderId;
  private String accountId;
  private List<String> productVariantIds;
}
