package com.minh.order_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindOrdersOfAccountQuery {
  private Integer page;
  private Integer size;
  private String accountId;
}
