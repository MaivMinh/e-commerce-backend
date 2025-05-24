package com.minh.product_service.command.commands;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
public class CreateCustomerCommand {
  @TargetAggregateIdentifier
  private String customerId;
  private String name;
  private String email;
  private String mobileNumber;
  private boolean activeSw;
}
