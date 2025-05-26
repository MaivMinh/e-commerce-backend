package com.minh.product_service.command.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteProductVariantCommand {
  private String id; // ID của biến thể sản phẩm cần xóa
  private String productId; // ID của sản phẩm chứa biến thể này
}
