package com.minh.order_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindOverallStatusOfCreatingOrderQuery {
  private String orderId;
}
