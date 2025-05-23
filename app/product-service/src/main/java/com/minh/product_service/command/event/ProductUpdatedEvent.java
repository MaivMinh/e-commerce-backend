package com.minh.product_service.command.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdatedEvent {
  private String id;
  private String name;
  private String description;
  private String cover;
}
