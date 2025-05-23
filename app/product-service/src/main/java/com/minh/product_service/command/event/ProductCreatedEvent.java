package com.minh.product_service.command.event;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCreatedEvent {
  private String id;
  private String name;
  private String description;
  private String cover;
}
