package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerCommand {
  @TargetAggregateIdentifier
  private String customerId;
  private String name;
  private String email;
  private String mobileNumber;
  private boolean activeSw;
}
