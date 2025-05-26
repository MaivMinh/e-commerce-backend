package com.minh.product_service.command.events;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantCreatedEvent {
  private String id;
  private String productId;
  private String size;
  private String colorName;
  private String colorHex;
  private Double price;
  private Integer quantity;
}
