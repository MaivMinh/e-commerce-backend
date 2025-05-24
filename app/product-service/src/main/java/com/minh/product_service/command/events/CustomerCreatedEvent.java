package com.minh.product_service.command.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerCreatedEvent {
  private String customerId;
  private String name;
  private String email;
  private String mobileNumber;
  private boolean activeSw;
}
