package com.minh.product_service.service;

import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.entity.Product;
import com.minh.product_service.mapper.ProductMapper;
import com.minh.product_service.repository.ProductRepository;
import com.minh.product_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.minh.product_service.specifications.ProductSpecs.containsDescription;
import static com.minh.product_service.specifications.ProductSpecs.containsName;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  /// Done.
  public void createProduct(Product product) {
    /// Validate again.
    try {
      productRepository.save(product);
    } catch (Exception e) {
      throw new RuntimeException("Error when save product to database");
    }
  }

  /// Done.
  public ResponseData fetchProducts(int page, int size, String sort) {
    /// Validate again.
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
      Page<Product> products = productRepository.findAll(pageable);
      List<ProductDTO> productDTOs = products.stream().map(product -> {
        ProductDTO productDTO = new ProductDTO();
        ProductMapper.mapToProductDTO(product, productDTO);
        return productDTO;
      }).collect(Collectors.toList());

      /// Nếu dùng như thông thường thì sẽ tạo ra Unmodifiable Map. Nó sẽ khiến XStream không thể serialize được.
      /// Do đó, ta phải tạo ra một Map mới bằng HashMap.
      Map<String, Object> data = new HashMap<>();
      data.put("totalElements", products.getTotalElements());
      data.put("totalPages", products.getTotalPages());
      data.put("page", products.getNumber());
      data.put("size", products.getSize());
      data.put("products", productDTOs);

      return ResponseData.builder()
              .message("Fetched products successfully")
              .status(HttpStatus.OK.value())
              .data(data)
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Error when fetch products from database");
    }
  }

  /// Done.
  public void updateProduct(Product product) {
    /// Validate again.
    Optional<Product> saved = productRepository.findById(product.getId());
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    /// Update product into database.
    try {
      productRepository.save(product);
    } catch (Exception e) {
      throw new RuntimeException("Error when update product to database. Try again");
    }
  }

  /// Done.
  public void deleteProduct(Product product) {
    /// Validate again.
    Optional<Product> saved = productRepository.findById(product.getId());
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    /// Update product into database.
    try {
      productRepository.delete(product);
    } catch (Exception e) {
      throw new RuntimeException("Error when delete product to database. Try again");
    }
  }

  /// Done.
  public ProductDTO fetchProduct(String id) {
    /// Validate again.
    Optional<Product> saved = productRepository.findById(id);
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    ProductDTO productDTO = new ProductDTO();
    ProductMapper.mapToProductDTO(saved.get(), productDTO);
    return productDTO;
  }

  /// Done.
  public ResponseData fetchProductsByCriteria(Map<String, String> criteria, String sort, int page, int size) {
    try {
      Specification<Product> specification = where(null);
      if (StringUtils.hasText(criteria.get("name"))) {
        specification = specification.and(containsName(criteria.get("name")));
      }
      if (StringUtils.hasText(criteria.get("description"))) {
        specification = specification.and(containsDescription(criteria.get("description")));
      }
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
      Page<Product> products = productRepository.findAll(specification, pageable);
      List<ProductDTO> productDTOs = products.stream().map(product -> {
        ProductDTO productDTO = new ProductDTO();
        ProductMapper.mapToProductDTO(product, productDTO);
        return productDTO;
      }).collect(Collectors.toList());

      /// Nếu dùng như thông thường thì sẽ tạo ra Unmodifiable Map. Nó sẽ khiến XStream không thể serialize được.
      /// Do đó, ta phải tạo ra một Map mới bằng HashMap.
      Map<String, Object> data = new HashMap<>();
      data.put("totalElements", products.getTotalElements());
      data.put("totalPages", products.getTotalPages());
      data.put("page", products.getNumber());
      data.put("size", products.getSize());
      data.put("products", productDTOs);

      return ResponseData.builder()
              .message("Fetched products successfully")
              .status(HttpStatus.OK.value())
              .data(data)
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Error when fetch products from database");
    }
  }
}
