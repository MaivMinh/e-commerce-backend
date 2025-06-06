package com.minh.order_service.entity;


import com.minh.order_service.DTOs.ProductVariantDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
  private String id;
  private String orderId;
  @NotEmpty(message = "Product variant ID cannot be empty")
  private ProductVariantDTO productVariantDTO;
  @NotNull(message = "Quantity cannot be null")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Integer quantity;
  @NotNull(message = "Price cannot be null")
  @PositiveOrZero(message = "Price must be zero or positive")
  private Double price;
  @NotNull(message = "Total cannot be null")
  @PositiveOrZero(message = "Total must be zero or positive")
  private Double total;
}