package com.minh.product_service.command.events;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
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
