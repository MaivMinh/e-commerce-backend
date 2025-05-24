package com.minh.product_service.mapper;

import com.minh.product_service.dto.CategoryDTO;
import com.minh.product_service.entity.Category;

public class CategoryMapper {
  public static void mapToCategoryDTO(Category category, CategoryDTO categoryDTO) {
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setDescription(category.getDescription());
  }

  public static void mapToCategory(CategoryDTO categoryDTO, Category category) {
    category.setId(categoryDTO.getId());
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());
  }
}
