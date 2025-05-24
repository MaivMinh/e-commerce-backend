package com.minh.product_service.command.events;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDeletedEvent {
  private String id;
}
