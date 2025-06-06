package com.minh.product_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {
  private String id;
  private String parentId;
  @NotEmpty(message = "Name is required")
  private String name;
  private String description;
  private String slug;
}
