package com.minh.order_service.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindAllOrdersQuery {
  private Integer page;
  private Integer size;
}
