package com.minh.product_service.query.queries;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchProductsByCategoryQuery {
  private String sort;
  private int page;
  private int size;
  private String categoryId;
}
