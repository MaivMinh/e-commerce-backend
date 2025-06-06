package com.minh.order_service.DTOs;

import jakarta.ws.rs.GET;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {
  private String id;
  private String name;
  private String cover;
  private String size;
  private String colorName;
  private Double price;
  private Integer quantity;
}
