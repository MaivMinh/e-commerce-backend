package com.minh.product_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
  private String id;
  @NotEmpty(message = "Name is required")
  @Size(min = 5, message = "The length of the product name should be at least 5 characters")
  private String name;
  private String slug;  /// Slug is generated automatically based on the name, so it can be empty here.
  @NotEmpty(message = "Description is required")
  @Size(max = 1000, message = "The length of the products description should be less than 1000")
  private String description;
  @NotEmpty(message = "Cover is required")
  private String cover;
  @NotEmpty(message = "Images are required")
  private List<String> images;
  @NotNull(message = "Price is required")
  private Double price;
  @NotNull(message = "Original price is required")
  private Double originalPrice;
  private String status;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
  @NotEmpty(message = "Category ID is required")
  private String categoryId;
  private List<ProductVariantDTO> productVariants;
}
