package com.minh.product_service.command.events;

import lombok.*;

@Data
public class CategoryUpdatedEvent {
  private String id;
  private String name;
  private String description;
}
