package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryCommand {
  @TargetAggregateIdentifier
  private String id;
  private String parentId;
  private String name;
  private String description;
  private String slug;
  private String image;
}
