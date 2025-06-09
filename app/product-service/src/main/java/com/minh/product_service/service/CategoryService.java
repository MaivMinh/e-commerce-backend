package com.minh.product_service.service;

import com.minh.product_service.command.events.CategoryDeletedEvent;
import com.minh.product_service.command.events.CategoryUpdatedEvent;
import com.minh.product_service.dto.CategoryDTO;
import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.entity.Category;
import com.minh.product_service.entity.Product;
import com.minh.product_service.mapper.CategoryMapper;
import com.minh.product_service.mapper.ProductMapper;
import com.minh.product_service.query.queries.FindAllCategoriesQuery;
import com.minh.product_service.query.queries.SearchProductsByCategoryQuery;
import com.minh.product_service.repository.CategoryRepository;
import com.minh.product_service.repository.ProductRepository;
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
  private final ProductRepository productRepository;

  private String generateSlug(String name) {
    if (name == null || name.isEmpty()) {
      return "";
    }
    // Convert to lowercase
    String slug = name.toLowerCase();
    // Handle Vietnamese accents and other diacritical marks
    slug = java.text.Normalizer.normalize(slug, java.text.Normalizer.Form.NFD);
    // Replace spaces with hyphens
    slug = slug.replaceAll("\\s+", "-");
    // Remove special characters but keep normalized characters
    slug = slug.replaceAll("[^\\p{ASCII}]", "");
    slug = slug.replaceAll("[^a-z0-9-]", "");
    // Replace multiple hyphens with a single one
    slug = slug.replaceAll("-+", "-");
    // Remove leading and trailing hyphens
    slug = slug.replaceAll("^-|-$", "");
    return slug;
  }

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
      if (!StringUtils.hasText(category.getSlug())) {
        String slug = generateSlug(category.getName());
        category.setSlug(slug);
      }
      categoryRepository.save(category);
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
      saved.setSlug(event.getSlug());
      saved.setImage(event.getImage());
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

  public ResponseData findAllCategories(FindAllCategoriesQuery query) {
    try {
      List<Category> categories = categoryRepository.findAll();
      List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
        CategoryDTO categoryDTO = new CategoryDTO();
        CategoryMapper.mapToCategoryDTO(category, categoryDTO);
        return categoryDTO;
      }).collect(Collectors.toList());
      return ResponseData.builder()
              .message("Fetched all categories successfully")
              .status(HttpStatus.OK.value())
              .data(categoryDTOs)
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch all categories");
    }
  }

  public ResponseData searchProductsByCategory(SearchProductsByCategoryQuery query) {
    Pageable pageable = null;
    if (StringUtils.hasText(query.getSort())) {
      /// sort = id:desc,name:asc
      List<Sort.Order> orders = new ArrayList<>();
      String[] sortFields = query.getSort().split(",");
      for (String field : sortFields) {
        orders.add(new Sort.Order(Sort.Direction.fromString(field.split(":")[1].toUpperCase()), field.split(":")[0]));
      }
      pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.by(orders));
    } else {
      pageable = PageRequest.of(query.getPage(), query.getSize());
    }

    Page<Product> productPage = productRepository.findProductByCategoryId(query.getCategoryId(), pageable);
    if (productPage.isEmpty()) {
      return ResponseData.builder()
              .message("No products found for this category")
              .status(HttpStatus.OK.value())
              .data(Collections.emptyList())
              .build();
    }

    List<Product> products = productPage.getContent();
    List<ProductDTO> productDTOs = products.stream()
            .map(product -> {
              ProductDTO productDTO = new ProductDTO();
              ProductMapper.mapToProductDTO(product, productDTO);
              return productDTO;
            }).collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("totalElements", productPage.getTotalElements());
    data.put("totalPages", productPage.getTotalPages());
    data.put("size", productPage.getSize());
    data.put("page", productPage.getNumber() + 1);
    data.put("products", productDTOs);
    return ResponseData.builder()
            .message("Fetched products by category successfully")
            .status(HttpStatus.OK.value())
            .data(data)
            .build();
  }
}
