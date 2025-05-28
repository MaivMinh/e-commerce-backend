package com.minh.product_service.dto;

import com.minh.product_service.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVariantMessageDTO {
  private String cartItemId;
  private String productVariantId;
  private String productName;
  private String productSlug;
  private String productCover;
  private String productVariantSize;
  private String productVariantColorName;
  private String productVariantColorHex;
  private Double productVariantPrice;
  private Double productVariantOriginalPrice;

  public ProductVariantMessageDTO(String productName, String productSlug, String productCover,
                                  String productVariantSize, String productVariantColorName, String productVariantColorHex,
                                  Double productVariantPrice, Double productVariantOriginalPrice) {
    this.productName = productName;
    this.productSlug = productSlug;
    this.productCover = productCover;
    this.productVariantSize = productVariantSize;
    this.productVariantColorName = productVariantColorName;
    this.productVariantColorHex = productVariantColorHex;
    this.productVariantPrice = productVariantPrice;
    this.productVariantOriginalPrice = productVariantOriginalPrice;
  }
}
