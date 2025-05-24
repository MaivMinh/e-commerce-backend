package com.minh.product_service.command.events;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
  private String id;
  private String name;
  private String description;
  private String cover;
}
