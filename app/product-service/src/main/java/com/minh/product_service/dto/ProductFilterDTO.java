package com.minh.product_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
  private List<String> categoryIds;
  private Double minPrice;
  private Double maxPrice;
  private Double rating;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
}
