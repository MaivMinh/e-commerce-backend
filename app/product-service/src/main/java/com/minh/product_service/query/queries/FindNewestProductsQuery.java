package com.minh.product_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindNewestProductsQuery {
  private Integer page;
  private Integer size;
}
