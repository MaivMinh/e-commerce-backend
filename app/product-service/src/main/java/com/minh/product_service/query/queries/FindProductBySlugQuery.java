package com.minh.product_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindProductBySlugQuery {
  private String slug;
}
