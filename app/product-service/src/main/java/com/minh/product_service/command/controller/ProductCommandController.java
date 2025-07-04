package com.minh.product_service.command.controller;

import com.minh.product_service.command.commands.*;
import com.minh.product_service.dto.ProductCreateDTO;
import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.dto.ProductVariantDTO;
import com.minh.product_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping(value = "/api/products", produces = {"application/json"})
@RequiredArgsConstructor
public class ProductCommandController {
  private final CommandGateway commandGateway;

  /// ================== ADMIN ROLE ================== ///
  /// Hàm thực hiện tạo mới một sản phẩm.
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createProduct(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
    /**
     *   {
     *   "name": "Product Name",
     *   "slug": "product-name",
     *   "categoryId": "category-id",
     *   "description": "Product description",
     *   "cover": "https://example.com/cover.jpg",
     *   "images": [
     *   "https://example.com/image1.jpg",
     *   "https://example.com/image2.jpg"
     *   ],
     *   "productVariants": [
     {
     "size": "M",
     "colorName": "Red",
     "colorHex": "#FF0000",
     "price": 100.0,
     "originalPrice": 120.0,
     "quantity": 10
     },
     *   ]
     *   "price": 100.0,
     *   "originalPrice": 120.0,
     *   "isFeatured": true,
     *   "isNew": false,
     *   "isBestSeller": false,
     *   "status": "active",
     *   }
     */

    CreateProductCommand command = CreateProductCommand.builder()
            .id(UUID.randomUUID().toString())
            .name(productCreateDTO.getName())
            .slug(productCreateDTO.getSlug())
            .categoryId(productCreateDTO.getCategoryId())
            .description(productCreateDTO.getDescription())
            .cover(productCreateDTO.getCover())
            .images(!productCreateDTO.getImages().isEmpty() ? productCreateDTO.getImages() : new ArrayList<>())
            .price(productCreateDTO.getPrice())
            .originalPrice(productCreateDTO.getOriginalPrice())
            .productVariants(productCreateDTO.getProductVariants() != null ? productCreateDTO.getProductVariants() : new ArrayList<>())
            .isFeatured(productCreateDTO.getIsFeatured())
            .isNew(productCreateDTO.getIsNew())
            .isBestseller(productCreateDTO.getIsBestseller())
            .status(productCreateDTO.getStatus())
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(201).body(new ResponseData(HttpStatus.CREATED.value(), "Product is created successfully", null));
  }

  /// Hàm thực hiện cập nhật thông tin một sản phẩm.
  /// DONE!
  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
    UpdateProductCommand command = UpdateProductCommand.builder()
            .id(productDTO.getId())
            .name(productDTO.getName())
            .slug(productDTO.getSlug())
            .cover(productDTO.getCover())
            .images(productDTO.getImages())
            .productVariants(productDTO.getProductVariants())
            .categoryId(productDTO.getCategoryId())
            .price(productDTO.getPrice())
            .originalPrice(productDTO.getOriginalPrice())
            .description(productDTO.getDescription())
            .status(productDTO.getStatus())
            .isBestseller(productDTO.getIsBestseller())
            .isFeatured(productDTO.getIsFeatured())
            .isNew(productDTO.getIsNew())
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product is updated successfully", null));
  }

  /// Hàm thực hiện xóa một sản phẩm.
  /// DONE!
  @DeleteMapping(value = "/{productId}")
  public ResponseEntity<ResponseData> deleteProduct(@PathVariable String productId) {
    DeleteProductCommand command = DeleteProductCommand.builder()
            .id(productId)
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product is deleted successfully", null));
  }


  /// Hàm thực hiện tạo rphẩm.a các variant của một sản
  @PostMapping(value = "/{productId}/variants")
  public ResponseEntity<ResponseData> createProductVariants(@RequestBody @Valid ProductVariantDTO productVariantDTO,
                                                            @PathVariable String productId) {
    /**
     *   {
     *   "size": "M",
     *   "colorName": "Red",
     *   "colorHex": "#FF0000",
     *   "price": 99999.99,
     *   "originalPrice": 109999.99,
     *   "quantity": 10
     *   }
     */
    CreateProductVariantCommand command = CreateProductVariantCommand.builder()
            .id(UUID.randomUUID().toString())
            .productId(productId)
            .size(productVariantDTO.getSize())
            .colorName(productVariantDTO.getColorName())
            .colorHex(productVariantDTO.getColorHex())
            .price(productVariantDTO.getPrice())
            .originalPrice(productVariantDTO.getOriginalPrice())
            .quantity(productVariantDTO.getQuantity())
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.CREATED.value(), "Product variant is created successfully", null));
  }

  /// Hàm thực hiện câp nhật thông tin một variant của sản phẩm.
  @PutMapping(value = "/{productId}/variants")
  public ResponseEntity<ResponseData> updateProductVariant(@PathVariable String productId,
                                                           @RequestBody @Valid ProductVariantDTO productVariantDTO) {

    if (!productVariantDTO.getProductId().equals(productId)) {
      return ResponseEntity.badRequest().body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Product id in path variable is not equal with product id in object", null));
    }


    UpdateProductVariantCommand command = UpdateProductVariantCommand.builder()
            .id(productVariantDTO.getId())
            .productId(productId)
            .size(productVariantDTO.getSize())
            .colorName(productVariantDTO.getColorName())
            .colorHex(productVariantDTO.getColorHex())
            .price(productVariantDTO.getPrice())
            .originalPrice(productVariantDTO.getOriginalPrice())
            .quantity(productVariantDTO.getQuantity())
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product variant is updated successfully", null));
  }

  /// Hàm thực hiện xóa một variant của sản phẩm.
  @DeleteMapping(value = "/{productId}/variants/{variantId}")
  public ResponseEntity<ResponseData> deleteProductVariant(@PathVariable String productId,
                                                           @PathVariable String variantId) {
    DeleteProductVariantCommand command = DeleteProductVariantCommand.builder()
            .id(variantId)
            .productId(productId)
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product variant is deleted successfully", null));
  }
}