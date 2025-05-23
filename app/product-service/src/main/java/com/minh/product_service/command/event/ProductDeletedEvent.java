package com.minh.product_service.command.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDeletedEvent {
  private String id;
}
