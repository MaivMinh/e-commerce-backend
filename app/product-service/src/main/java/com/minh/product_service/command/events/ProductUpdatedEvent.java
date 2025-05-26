package com.minh.product_service.command.events;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdatedEvent {
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
}
