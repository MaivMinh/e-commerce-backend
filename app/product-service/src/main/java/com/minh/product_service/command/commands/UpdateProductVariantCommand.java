package com.minh.product_service.command.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateProductVariantCommand {
  private String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;
}
