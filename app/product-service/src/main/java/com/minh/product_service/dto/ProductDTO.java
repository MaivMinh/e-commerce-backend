package com.minh.product_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
  private String id;

  @NotEmpty(message = "Name is required")
  @Size(min = 5, max = 30, message = "The length of the products name should be between 5 and 30")
  private String name;

  @NotEmpty(message = "Description is required")
  @Size(max = 1000, message = "The length of the products description should be less than 1000")
  private String description;

  @NotEmpty(message = "Cover image is required")
  private String cover;
}