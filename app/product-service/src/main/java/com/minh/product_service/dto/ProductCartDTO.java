package com.minh.product_service.dto;
import lombok.*;
/// Class lưu thông tin sản phẩm trong giỏ hàng. Kết hợp thông tin từ ProductDTO, ProductVariantDTO.
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartDTO {
  private String productVariantId;
  private String productName;
  private String productSlug;
  private Double productVariantPrice;
  private Double productVariantOriginalPrice;
  private String productCover;
  private String productVariantSize;
  private String productVariantColorName;
  private String productVariantColorHex;
}
