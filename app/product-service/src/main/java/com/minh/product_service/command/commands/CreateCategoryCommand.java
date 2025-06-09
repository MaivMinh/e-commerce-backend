package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCategoryCommand {
  @TargetAggregateIdentifier
  private String id;
  private String parentId;
  private String name;
  private String description;
  private String slug;
  private String image;
}
