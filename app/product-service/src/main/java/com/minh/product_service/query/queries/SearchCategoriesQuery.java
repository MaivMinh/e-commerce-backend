package com.minh.product_service.query.queries;

import lombok.*;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchCategoriesQuery {
  private Map<String, String> criteria;
  private String sort;
  private int page;
  private int size;
}
