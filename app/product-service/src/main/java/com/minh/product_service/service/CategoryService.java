package com.minh.product_service.service;

import com.minh.product_service.command.events.CategoryDeletedEvent;
import com.minh.product_service.command.events.CategoryUpdatedEvent;
import com.minh.product_service.dto.CategoryDTO;
import com.minh.product_service.entity.Category;
import com.minh.product_service.mapper.CategoryMapper;
import com.minh.product_service.repository.CategoryRepository;
import com.minh.product_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  /// Hàm thêm mới danh mục sản phẩm.
  /// DONE!!!
  public void createCategory(Category category) {
    /// Validate category.
    Optional<Category> saved = categoryRepository.findById(category.getId());
    if (saved.isPresent()) {
      throw new RuntimeException("Category already exists");
    }
    /// Save category.
    try {
      Category cate = categoryRepository.save(category);
      System.out.println("New category with id: " + cate.getId());
    } catch (Exception e) {
      throw new RuntimeException("Failed to create new category");
    }
  }

  /// Hàm cập nhật danh mục sản phẩm.
  public void updateCategory(CategoryUpdatedEvent event) {
    Category saved = categoryRepository.findById(event.getId()).orElseThrow(
            () -> new RuntimeException("Category not found")
    );
    try {
      saved.setName(event.getName());
      saved.setParentId(event.getParentId());
      saved.setDescription(event.getDescription());
      categoryRepository.save(saved);
    } catch (Exception e) {
      throw new RuntimeException("Failed to update category");
    }
  }
  public void deleteCategory(CategoryDeletedEvent event) {
    Category saved = categoryRepository.findById(event.getId()).orElseThrow(
            () -> new RuntimeException("Category not found")
    );
    try {
      categoryRepository.delete(saved);
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete category");
    }
  }

  /// DONE!!!
  public ResponseData fetchCategory(String id) {
    try {
      Optional<Category> category = categoryRepository.findById(id);
      if (category.isEmpty()) {
        throw new RuntimeException("Category not found");
      }
      CategoryDTO categoryDTO = new CategoryDTO();
      CategoryMapper.mapToCategoryDTO(category.get(), categoryDTO);
      return ResponseData.builder()
              .message("Success")
              .status(HttpStatus.OK.value())
              .data(categoryDTO)
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch category");
    }
  }

  /// Hàm lấy danh sách tất cả các danh mục. (Có phân trang)
  /// DONE!!!
  public ResponseData fetchCategories(int page, int size, String sort) {
    try {
      Pageable pageable = null;
      if (StringUtils.hasText(sort)) {
        /// sort = id:desc,name:asc
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortFields = sort.split(",");
        for (String field : sortFields) {
          orders.add(new Sort.Order(Sort.Direction.fromString(field.split(":")[1].toUpperCase()), field.split(":")[0]));
        }
        pageable = PageRequest.of(page, size, Sort.by(orders));
      } else pageable = PageRequest.of(page, size);

      Page<Category> categories = categoryRepository.findAll(pageable);
      List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
        CategoryDTO categoryDTO = new CategoryDTO();
        CategoryMapper.mapToCategoryDTO(category, categoryDTO);
        return categoryDTO;
      }).collect(Collectors.toList());
      Map<String, Object> data = new HashMap<>();
      data.put("totalElements", categories.getTotalElements());
      data.put("totalPages", categories.getTotalPages());
      data.put("size", categories.getSize());
      data.put("page", categories.getNumber() + 1);
      data.put("categories", categoryDTOs);
      return ResponseData.builder()
              .message("Fetched categories successfully")
              .status(HttpStatus.OK.value())
              .data(data)
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch categories");
    }
  }
}
