package com.minh.product_service.query.queries;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SearchProductsQuery {
  private int page;
  private int size;
  private String sort;
  private String name;
}
