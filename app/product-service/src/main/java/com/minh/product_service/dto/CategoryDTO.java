package com.minh.product_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
  private String id;
  private String parentId;
  @NotEmpty(message = "Category name must not be empty")
  @Size(max = 100, message = "Category name must not exceed 100 characters")
  private String name;
  @NotEmpty(message = "Category description must not be empty")
  @Size(max = 1000, message = "Category description must not exceed 1000 characters")
  private String description;
}
