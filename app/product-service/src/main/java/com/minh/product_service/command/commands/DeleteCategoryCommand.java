package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCategoryCommand {
  @TargetAggregateIdentifier
  private String id; // ID của danh mục sản phẩm cần xóa.
}
