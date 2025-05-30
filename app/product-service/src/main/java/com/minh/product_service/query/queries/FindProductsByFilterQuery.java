package com.minh.product_service.query.queries;

import com.minh.product_service.dto.ProductFilterDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FindProductsByFilterQuery {
  private ProductFilterDTO filter;
  private Integer page;
  private Integer size;
  private String sort;
}
