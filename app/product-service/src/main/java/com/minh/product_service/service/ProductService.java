package com.minh.product_service.service;

import com.minh.product_service.command.events.ProductCreatedEvent;
import com.minh.product_service.command.events.ProductDeletedEvent;
import com.minh.product_service.command.events.ProductUpdatedEvent;
import com.minh.product_service.command.events.ProductWithProductVariantsCreatedEvent;
import com.minh.product_service.dto.*;
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

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  /// Done.
  public void createProduct(ProductCreatedEvent event) {
    try {
      /// Validate again.
      Product product = new Product();
      product.setId(event.getId());
      product.setName(event.getName());
      if (StringUtils.hasText(event.getSlug())) {
        product.setSlug(event.getSlug());
      } else {
        product.setSlug(this.generateSlug(event.getName()));
      }
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

      for (String image : event.getImages()) {
        ProductImage productImage = new ProductImage();
        productImage.setId(UUID.randomUUID().toString());
        productImage.setProductId(product.getId());
        productImage.setUrl(image);
        productImageRepository.save(productImage);
      }

      for (ProductVariantDTO variant : event.getProductVariants()) {
        com.minh.product_service.entity.ProductVariant productVariant = new com.minh.product_service.entity.ProductVariant();
        productVariant.setId(UUID.randomUUID().toString());
        productVariant.setProductId(product.getId());
        productVariant.setSize(variant.getSize());
        productVariant.setColorName(variant.getColorName());
        productVariant.setColorHex(variant.getColorHex());
        productVariant.setPrice(variant.getPrice());
        productVariant.setOriginalPrice(variant.getOriginalPrice());
        productVariant.setQuantity(variant.getQuantity());
        productVariantRepository.save(productVariant);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException("Error when save product with variants to database");
    }
  }


  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
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

    /// Update images.
    if (event.getImages() == null || event.getImages().isEmpty()) {
      /// Clear old images.
      productImageRepository.deleteAllByProductId(productId);
    } else {
      /// Clear old images.
      log.warn("Update new images for product ID: {}", productId);
      productImageRepository.deleteAllByProductId(productId);
      /// Save new images.
      for (String image : event.getImages()) {
        ProductImage productImage = new ProductImage();
        productImage.setId(UUID.randomUUID().toString());
        productImage.setProductId(productId);
        productImage.setUrl(image);
        productImageRepository.save(productImage);
      }
    }

    /// Update product variants.
    if (event.getProductVariants() == null || event.getProductVariants().isEmpty()) {
      /// Clear old product variants.
      productVariantRepository.deleteAllByProductId(productId);
    } else {
      log.warn("Update new variants for product ID: {}", productId);
      /// Clear old product variants.
      productVariantRepository.deleteAllByProductId(productId);
      /// Save new product variants.
      for (ProductVariantDTO variant : event.getProductVariants()) {
        com.minh.product_service.entity.ProductVariant productVariant = new com.minh.product_service.entity.ProductVariant();
        productVariant.setId(UUID.randomUUID().toString());
        productVariant.setProductId(productId);
        productVariant.setSize(variant.getSize());
        productVariant.setColorName(variant.getColorName());
        productVariant.setColorHex(variant.getColorHex());
        productVariant.setPrice(variant.getPrice());
        productVariant.setOriginalPrice(variant.getOriginalPrice());
        productVariant.setQuantity(variant.getQuantity());
        productVariantRepository.save(productVariant);
      }
    }
    productRepository.save(product);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
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

      /// Delete images of product and product variants.
      productImageRepository.deleteAllByProductId(product.getId());
      productVariantRepository.deleteAllByProductId(product.getId());
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

    /// Get product variants.
    List<com.minh.product_service.entity.ProductVariant> variants = productVariantRepository.findAllByProductId(id);
    List<ProductVariantDTO> productVariants = variants.stream().map(variant -> {
      ProductVariantDTO variantDTO = new ProductVariantDTO();
      variantDTO.setId(variant.getId());
      variantDTO.setSize(variant.getSize());
      variantDTO.setColorName(variant.getColorName());
      variantDTO.setColorHex(variant.getColorHex());
      variantDTO.setPrice(variant.getPrice());
      variantDTO.setOriginalPrice(variant.getOriginalPrice());
      variantDTO.setQuantity(variant.getQuantity());
      return variantDTO;
    }).collect(Collectors.toList());
    productDTO.setProductVariants(productVariants);

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
                List<com.minh.product_service.entity.ProductVariant> variants = productVariantRepository.findAllByProductId(product.getId());

                List<ProductVariantDTO> productVariants = variants.stream().map(variant -> {
                  ProductVariantDTO variantDTO = new ProductVariantDTO();
                  variantDTO.setId(variant.getId());
                  variantDTO.setSize(variant.getSize());
                  variantDTO.setColorName(variant.getColorName());
                  variantDTO.setColorHex(variant.getColorHex());
                  variantDTO.setPrice(variant.getPrice());
                  variantDTO.setOriginalPrice(variant.getOriginalPrice());
                  variantDTO.setQuantity(variant.getQuantity());
                  return variantDTO;
                }).collect(Collectors.toList());
                productDTO.setProductVariants(productVariants);
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

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
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

    /// Get product variants.
    List<com.minh.product_service.entity.ProductVariant> variants = productVariantRepository.findAllByProductId(saved.get().getId());
    List<ProductVariantDTO> productVariants = variants.stream().map(variant -> {
      ProductVariantDTO variantDTO = new ProductVariantDTO();
      variantDTO.setId(variant.getId());
      variantDTO.setSize(variant.getSize());
      variantDTO.setColorName(variant.getColorName());
      variantDTO.setColorHex(variant.getColorHex());
      variantDTO.setPrice(variant.getPrice());
      variantDTO.setOriginalPrice(variant.getOriginalPrice());
      variantDTO.setQuantity(variant.getQuantity());
      return variantDTO;
    }).collect(Collectors.toList());
    productDTO.setProductVariants(productVariants);

    return productDTO;
  }

  /// Hàm thực hiện tìm kiếm biến thể sản phẩm theo ID.
  public FindProductVariantByIdResponse findProductVariantById(FindProductVariantByIdRequest request) {
    try {
      String productVariantId = request.getProductVariantId();
      Optional<com.minh.product_service.entity.ProductVariant> productVariant =
              productVariantRepository.findById(productVariantId);

      if (productVariant.isPresent()) {
        com.minh.product_service.entity.ProductVariant variant = productVariant.get();

        // Get product information
        Optional<Product> product = productRepository.findById(variant.getProductId());
        if (product.isEmpty()) {
          return FindProductVariantByIdResponse.newBuilder()
                  .setStatus(HttpStatus.NOT_FOUND.value())
                  .setMessage("Product not found for this variant")
                  .build();
        }

        return FindProductVariantByIdResponse.newBuilder()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success")
                .setProductVariant(ProductVariant.newBuilder()
                        .setId(variant.getId())
                        .setName(product.get().getName())
                        .setSlug(product.get().getSlug())
                        .setCover(product.get().getCover())
                        .setPrice(variant.getPrice())
                        .setOriginalPrice(variant.getOriginalPrice())
                        .setSize(variant.getSize())
                        .setColorName(variant.getColorName())
                        .setColorHex(variant.getColorHex())
                        .build())
                .build();
      }

      return FindProductVariantByIdResponse.newBuilder()
              .setStatus(HttpStatus.NOT_FOUND.value())
              .setMessage("Product variant not found")
              .build();
    } catch (Exception e) {
      log.error("Error occurred while finding product variant by ID: {}", e.getMessage(), e);
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

      if (dtos.isEmpty()) {
        return FindProductVariantsByIdsResponse.newBuilder()
                .setStatus(HttpStatus.OK.value())
                .setMessage("No product variants found for the provided IDs")
                .build();
      }

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
    if (products.isEmpty()) {
      return new ResponseData(HttpStatus.OK.value(), "No products found", Collections.emptyMap());
    }

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
}