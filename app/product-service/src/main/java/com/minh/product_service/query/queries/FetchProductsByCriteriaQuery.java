package com.minh.product_service.query.queries;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchProductsByCriteriaQuery {
  private Map<String, String> criteria;
  private int page;
  private int size;
  private String sort;
}
