package com.minh.product_service.command.events;


import com.minh.product_service.dto.ProductVariantDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
  private String id;
  private String name;
  private String slug;
  private String description;
  private String cover;
  private List<String> images;
  private Double price;
  private Double originalPrice;
  private List<ProductVariantDTO> productVariants;
  private String status;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
  private String categoryId;
}
