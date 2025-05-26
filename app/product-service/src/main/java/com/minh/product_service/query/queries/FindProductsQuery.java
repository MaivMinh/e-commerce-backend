package com.minh.product_service.query.queries;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindProductsQuery {
  private String sort;
  private int page;
  private int size;
}
