package com.minh.product_service.query.queries;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FetchProductsQuery {
  private int page;
  private int size;
  private String sort;
}
