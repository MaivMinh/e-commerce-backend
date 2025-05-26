package com.minh.product_service.service;

import com.minh.product_service.command.events.ProductVariantCreatedEvent;
import com.minh.product_service.command.events.ProductVariantUpdatedEvent;
import com.minh.product_service.dto.ProductVariantDTO;
import com.minh.product_service.entity.Product;
import com.minh.product_service.entity.ProductVariant;
import com.minh.product_service.query.queries.FindVariantQuery;
import com.minh.product_service.query.queries.FindVariantsByProductIdQuery;
import com.minh.product_service.query.queries.FindVariantsQuery;
import com.minh.product_service.repository.ProductVariantRepository;
import com.minh.product_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantService {
  private final ProductVariantRepository productVariantRepository;

  public void createProductVariant(ProductVariantCreatedEvent event) {
    try {
      /// Create new product variant entity.
      ProductVariant productVariant = ProductVariant.builder()
              .id(event.getId())
              .productId(event.getProductId())
              .size(event.getSize())
              .colorName(event.getColorName())
              .colorHex(event.getColorHex())
              .price(event.getPrice())
              .quantity(event.getQuantity())
              .build();
      /// Save product variant to database.
      productVariantRepository.save(productVariant);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to create product variant: " + e.getMessage());
    }
  }

  public void updateProductVariant(ProductVariantUpdatedEvent event) {
    try {
      /// Find product variant by id.
      ProductVariant productVariant = productVariantRepository.findById(event.getId())
              .orElseThrow(() -> new RuntimeException("Product variant not found: " + event.getId()));
      /// Update product variant entity.
      productVariant.setProductId(event.getProductId());
      productVariant.setSize(event.getSize());
      productVariant.setColorName(event.getColorName());
      productVariant.setColorHex(event.getColorHex());
      productVariant.setPrice(event.getPrice());
      productVariant.setQuantity(event.getQuantity());
      /// Save updated product variant to database.
      productVariantRepository.save(productVariant);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to update product variant: " + e.getMessage());
    }
  }

  public ResponseData findVariant(FindVariantQuery query) {
    try {
      /// Find product variant by id.
      ProductVariant productVariant = productVariantRepository.findById(query.getVariantId())
              .orElseThrow(() -> new RuntimeException("Product variant not found: " + query.getVariantId()));
      /// Return response data with product variant.
      ProductVariantDTO productVariantDTO = ProductVariantDTO.builder()
              .id(productVariant.getId())
              .productId(productVariant.getProductId())
              .size(productVariant.getSize())
              .colorName(productVariant.getColorName())
              .colorHex(productVariant.getColorHex())
              .price(productVariant.getPrice())
              .quantity(productVariant.getQuantity())
              .build();

      return ResponseData.builder()
              .status(200)
              .message("Product variant found")
              .data(productVariantDTO)
              .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to find product variant: " + e.getMessage());
    }
  }

  public ResponseData findVariants(FindVariantsQuery query) {
    try {
      Pageable pageable = null;
      if (StringUtils.hasText(query.getSort())) {
        /// sort = id:desc,name:asc
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortFields = query.getSort().split(",");
        for (String field : sortFields) {
          orders.add(new Sort.Order(Sort.Direction.fromString(field.split(":")[1].toUpperCase()), field.split(":")[0]));
        }
        pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.by(orders));
      } else pageable = PageRequest.of(query.getPage(), query.getSize());

      /// Find all product variants with pagination and sorting.
      Page<ProductVariant> productVariants = productVariantRepository.findAll(pageable);
      List<ProductVariantDTO> productVariantDTOs = productVariants.stream().map(productVariant -> ProductVariantDTO.builder()
              .id(productVariant.getId())
              .productId(productVariant.getProductId())
              .size(productVariant.getSize())
              .colorName(productVariant.getColorName())
              .colorHex(productVariant.getColorHex())
              .price(productVariant.getPrice())
              .quantity(productVariant.getQuantity())
              .build()).collect(Collectors.toList());

      /// Return response data with product variants.
      Map<String, Object> data = new HashMap<>();
      data.put("totalElements", String.valueOf(productVariants.getTotalElements()));
      data.put("totalPages", String.valueOf(productVariants.getTotalPages()));
      data.put("variants", productVariantDTOs);
      data.put("size", productVariants.getSize());
      data.put("page", productVariants.getNumber() + 1);
      return ResponseData.builder()
              .status(200)
              .message("Product variants found")
              .data(data)
              .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to find product variants: " + e.getMessage());
    }
  }

  public ResponseData findVariantsByProductId(FindVariantsByProductIdQuery query) {
    try {
      /// Find all product variants by product id.
      List<ProductVariant> productVariants = productVariantRepository.findByProductId(query.getProductId());
      if (productVariants.isEmpty()) {
        return ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No product variants found for product id: " + query.getProductId())
                .data(null)
                .build();
      }

      List<ProductVariantDTO> productVariantDTOs = productVariants.stream().map(productVariant -> ProductVariantDTO.builder()
              .id(productVariant.getId())
              .productId(productVariant.getProductId())
              .size(productVariant.getSize())
              .colorName(productVariant.getColorName())
              .colorHex(productVariant.getColorHex())
              .price(productVariant.getPrice())
              .quantity(productVariant.getQuantity())
              .build()).collect(Collectors.toList());

      return ResponseData.builder()
              .status(200)
              .message("Success")
              .data(productVariantDTOs)
              .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to find product variants by product id: " + e.getMessage());
    }
  }
}
