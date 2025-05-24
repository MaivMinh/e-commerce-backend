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
  private String description;
  private String cover;
}
