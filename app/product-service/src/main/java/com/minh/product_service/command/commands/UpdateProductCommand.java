package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
  @TargetAggregateIdentifier
  private String id;
  private String name;
  private String description;
  private String cover;
}
