package com.minh.product_service.command.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerUpdatedEvent {
  private String customerId;
  private String name;
  private String email;
  private String mobileNumber;
  private boolean activeSw;
}