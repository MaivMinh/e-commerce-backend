package com.minh.promotion_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindPromotionsWithParamsQuery {
  String status;
  String search;
  Integer page;
  Integer size;
}
