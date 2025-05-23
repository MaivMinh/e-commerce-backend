package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductCommand {
  @TargetAggregateIdentifier
  private String id;
}
