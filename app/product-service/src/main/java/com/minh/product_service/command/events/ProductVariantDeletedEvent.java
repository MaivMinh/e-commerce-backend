package com.minh.product_service.command.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVariantDeletedEvent {
  private String id; // ID của biến thể sản phẩm cần xóa
  private String productId; // ID của sản phẩm chứa biến thể này
}
