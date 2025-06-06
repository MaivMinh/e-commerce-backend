package com.minh.payment_service.query.queries;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllPaymentMethodsQuery {
    // This class can be extended in the future to include pagination or filtering parameters
    // For now, it serves as a marker for the query to find all payment methods
  private String search;
  private String filter;
  private Boolean isActive;
  private int size;
  private int page;
}
