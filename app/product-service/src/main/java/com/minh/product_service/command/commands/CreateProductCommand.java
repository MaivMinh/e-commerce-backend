package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {
  @TargetAggregateIdentifier
  private String id;
  private String name;
  private String slug;
  private String description;
  private String cover;
  private Double price;
  private Double originalPrice;
  private String status;
  private Boolean isFeatured;
  private Boolean isNew;
  private Boolean isBestseller;
  private String categoryId;
  private List<String> images;
}
