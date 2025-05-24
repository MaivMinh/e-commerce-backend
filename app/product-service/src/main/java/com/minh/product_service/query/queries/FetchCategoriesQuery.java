package com.minh.product_service.query.queries;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FetchCategoriesQuery {
  private int page;
  private int size;
  private String sort;
}
