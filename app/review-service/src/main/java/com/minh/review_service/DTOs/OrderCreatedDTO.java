package com.minh.review_service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreatedDTO {
  private String accountId;
  private List<String> productVariantIds;
}
