package com.minh.payment_service.query.queries;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPaymentMethodsByTypeQuery {
  private String type;
}
