package com.minh.product_service.query.queries;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchCategoriesQuery {
  private String name;
  private int page;
  private int size;
}
