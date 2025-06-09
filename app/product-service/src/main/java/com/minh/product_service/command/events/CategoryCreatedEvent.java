package com.minh.product_service.command.events;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreatedEvent {
  private String id;
  private String parentId;
  private String name;
  private String description;
  private String slug;
  private String image;
}
