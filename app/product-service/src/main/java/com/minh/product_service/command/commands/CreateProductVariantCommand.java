package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateProductVariantCommand {
  @TargetAggregateIdentifier
  private String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;
}
