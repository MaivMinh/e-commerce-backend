package com.minh.product_service.command.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductCommand {
  @TargetAggregateIdentifier
  private Long id;
  private String name;
  private String description;
  private Double price;
  private MultipartFile cover;
}
