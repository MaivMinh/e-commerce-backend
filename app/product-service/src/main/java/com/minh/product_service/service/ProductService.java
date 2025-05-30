package com.minh.product_service.service;

import com.minh.product_service.command.events.ProductCreatedEvent;
import com.minh.product_service.command.events.ProductDeletedEvent;
import com.minh.product_service.command.events.ProductUpdatedEvent;
import com.minh.product_service.dto.ProductCartDTO;
import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.dto.ProductFilterDTO;
import com.minh.product_service.dto.ProductVariantMessageDTO;
import com.minh.product_service.entity.Product;
import com.minh.product_service.entity.ProductImage;
import com.minh.product_service.entity.ProductStatus;
import com.minh.product_service.mapper.ProductMapper;
import com.minh.product_service.query.queries.FindProductBySlugQuery;
import com.minh.product_service.query.queries.FindProductsByFilterQuery;
import com.minh.product_service.query.queries.FindProductsQuery;
import com.minh.product_service.repository.ProductImageRepository;
import com.minh.product_service.repository.ProductRepository;
import com.minh.product_service.repository.ProductVariantRepository;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service_grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.minh.product_service.specifications.ProductSpecification.hasCategoryIds;
import static com.minh.product_service.specifications.ProductSpecification.hasPriceBetween;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final ProductVariantRepository productVariantRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  /// Done.
  public void createProduct(ProductCreatedEvent event) {
    try {
      /// Validate again.
      Product product = new Product();
      product.setId(event.getId());
      product.setName(event.getName());
      product.setSlug(event.getSlug());
      product.setDescription(event.getDescription());
      product.setCover(event.getCover());
      product.setPrice(event.getPrice());
      product.setOriginalPrice(event.getOriginalPrice());
      product.setIsFeatured(event.getIsFeatured());
      product.setIsBestseller(event.getIsBestseller());
      product.setIsNew(event.getIsNew());
      product.setCategoryId(event.getCategoryId());
      ProductStatus status = ProductStatus.valueOf(event.getStatus().toLowerCase());
      product.setStatus(status);
      productRepository.save(product);  /// Ensure that product is saved before saving images.
      System.out.println(event.getImages().size());
      for (String image : event.getImages()) {
        ProductImage productImage = new ProductImage();
        productImage.setId(UUID.randomUUID().toString());
        productImage.setProductId(product.getId());
        productImage.setUrl(image);
        productImageRepository.save(productImage);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Error when save product to database");
    }
  }


  /// Done.
  public void updateProduct(ProductUpdatedEvent event) {
    String productId = event.getId();
    Optional<Product> saved = productRepository.findById(productId);
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    Product product = saved.get();
    product.setName(event.getName());
    product.setDescription(event.getDescription());
    product.setPrice(event.getPrice());
    product.setOriginalPrice(event.getOriginalPrice());
    product.setIsFeatured(event.getIsFeatured());
    product.setIsBestseller(event.getIsBestseller());
    product.setIsNew(event.getIsNew());
    product.setSlug(event.getSlug());
    product.setCategoryId(event.getCategoryId());
    ProductStatus status = ProductStatus.valueOf(event.getStatus().toLowerCase());
    product.setStatus(status);
    product.setCover(event.getCover());
    try {
      productRepository.save(product);
    } catch (Exception e) {
      throw new RuntimeException("Error when update product to database");
    }
  }

  /// Done.
  public void deleteProduct(ProductDeletedEvent event) {
    /// Validate again.
    Optional<Product> saved = productRepository.findById(event.getId());
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    /// Update product into database.
    try {
      Product product = saved.get();
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
    /// Get images of product.
    List<String> images = new ArrayList<>(productImageRepository.findImagesByProductId(id));
    productDTO.setImages(images);
    return productDTO;
  }

  public ResponseData findProducts(FindProductsQuery query) {
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
      Page<Product> products = productRepository.findAll(pageable);
      List<ProductDTO> productDTOs = products.stream()
              .map(product -> {
                ProductDTO productDTO = new ProductDTO();
                ProductMapper.mapToProductDTO(product, productDTO);
                List<String> images = new ArrayList<>(productImageRepository.findImagesByProductId(product.getId()));
                productDTO.setImages(images);
                return productDTO;
              }).collect(Collectors.toList());

      Map<String, Object> data = new HashMap<>();
      data.put("products", productDTOs);
      data.put("size", products.getSize());
      data.put("page", products.getNumber() + 1);
      data.put("totalElements", products.getTotalElements());
      data.put("totalPages", products.getTotalPages());

      return new ResponseData(HttpStatus.OK.value(), "Success", data);
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while fetching products: " + e.getMessage());
    }
  }

  public ProductDTO findProductBySlug(FindProductBySlugQuery query) {
    /// Validate again.
    Optional<Product> saved = productRepository.findProductBySlug((query.getSlug()));
    if (saved.isEmpty()) {
      throw new RuntimeException("Product not found");
    }
    ProductDTO productDTO = new ProductDTO();
    ProductMapper.mapToProductDTO(saved.get(), productDTO);
    /// Get images of product.
    List<String> images = new ArrayList<>(productImageRepository.findImagesByProductId(saved.get().getId()));
    productDTO.setImages(images);
    return productDTO;
  }

  /// Hàm thực hiện tìm kiếm biến thể sản phẩm theo ID.
  public FindProductVariantByIdResponse findProductVariantById(FindProductVariantByIdRequest request) {
    try {
      String productVariantId = request.getProductVariantId();
      Optional<ProductCartDTO> productCartDTO = productVariantRepository.findProductCartDTOByProductVariantId(productVariantId);
      if (productCartDTO.isPresent()) {
        ProductCartDTO dto = productCartDTO.get();
        return FindProductVariantByIdResponse.newBuilder()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success")
                .setProductVariant(ProductVariant.newBuilder()
                        .setId(dto.getProductVariantId())
                        .setName(dto.getProductName())
                        .setSlug(dto.getProductSlug())
                        .setCover(dto.getProductCover())
                        .setPrice(dto.getProductVariantPrice())
                        .setOriginalPrice(dto.getProductVariantOriginalPrice())
                        .setSize(dto.getProductVariantSize())
                        .setColorName(dto.getProductVariantColorName())
                        .setColorHex(dto.getProductVariantColorHex())
                        .build())
                .build();
      }

      return null;
    } catch (Exception e) {
      log.error("Error occurred while finding product variant by ID: {}", e.getMessage());
      return FindProductVariantByIdResponse.newBuilder()
              .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .setMessage("Error occurred while finding product variant by ID: " + e.getMessage())
              .build();
    }
  }

  public FindProductVariantsByIdsResponse findProductVariantsByIds(FindProductVariantsByIdsRequest request) {
    try {
      List<ProductVariantMessageDTO> dtos = request.getCartItemsList().stream().map(item -> {
        ProductVariantMessageDTO dto = productVariantRepository.findProductVariantById(item.getProductVariantId());
        dto.setCartItemId(item.getCartItemId());
        dto.setProductVariantId(item.getProductVariantId());
        return dto;
      }).toList();
      return FindProductVariantsByIdsResponse.newBuilder()
              .setStatus(HttpStatus.OK.value())
              .setMessage("Success")
              .addAllProductVariants(dtos.stream().map(dto -> ProductVariantMessage.newBuilder()
                      .setCartItemId(dto.getCartItemId())
                      .setProductVariantId(dto.getProductVariantId())
                      .setProductName(dto.getProductName())
                      .setProductSlug(dto.getProductSlug())
                      .setProductCover(dto.getProductCover())
                      .setProductVariantSize(dto.getProductVariantSize())
                      .setProductVariantColorName(dto.getProductVariantColorName())
                      .setProductVariantColorHex(dto.getProductVariantColorHex())
                      .setProductVariantPrice(dto.getProductVariantPrice())
                      .setProductVariantOriginalPrice(dto.getProductVariantOriginalPrice())
                      .build()).collect(Collectors.toList()))
              .build();
    } catch (Exception e) {
      log.error("Error occurred while finding product variants by IDs: {}", e.getMessage());
      return FindProductVariantsByIdsResponse.newBuilder()
              .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .setMessage("Error occurred while finding product variants by IDs: " + e.getMessage())
              .build();
    }
  }

  public ResponseData findProductsByFilter(FindProductsByFilterQuery query) {
    Specification<Product> spec = Specification.where(null);
    ProductFilterDTO filter = query.getFilter();
    if (!filter.getCategoryIds().isEmpty()) {
      spec = spec.and(hasCategoryIds(filter.getCategoryIds()));
    }
    spec = spec.and(hasPriceBetween(filter.getMinPrice(), filter.getMaxPrice()));

    Pageable pageable = null;
    if (StringUtils.hasText(query.getSort())) {
      /// sort = soldItems:desc,price:asc,price:desc,newest:desc
      List<Sort.Order> orders = new ArrayList<>();
      String[] sortFields = query.getSort().split(",");
      for (String field : sortFields) {
        orders.add(new Sort.Order(Sort.Direction.fromString(field.split(":")[1].toUpperCase()), field.split(":")[0]));
      }
      pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.by(orders));
    } else {
      pageable = PageRequest.of(query.getPage(), query.getSize());
    }

    Page<Product> products = productRepository.findAll(spec, pageable);
    List<ProductDTO> productDTOs = products.stream()
            .map(product -> {
              ProductDTO productDTO = new ProductDTO();
              ProductMapper.mapToProductDTO(product, productDTO);
              return productDTO;
            }).collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("products", productDTOs);
    data.put("size", products.getSize());
    data.put("page", products.getNumber() + 1);
    data.put("totalElements", products.getTotalElements());
    data.put("totalPages", products.getTotalPages());
    return new ResponseData(HttpStatus.OK.value(), "Success", data);
  }
}