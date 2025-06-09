package com.minh.product_service.command.events;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdatedEvent {
  private String id;
  private String parentId; // Optional, can be null if no parent category.
  private String name;
  private String description;
  private String slug;
  private String image;
}
