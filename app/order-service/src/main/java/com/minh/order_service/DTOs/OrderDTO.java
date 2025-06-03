package com.minh.order_service.DTOs;

import com.minh.order_service.entity.OrderItemDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private String id;
  @NotEmpty(message = "Account ID cannot be empty")
  private String accountId;
  @NotEmpty(message = "Shipping address cannot be empty")
  private String shippingAddress;
  @NotEmpty(message = "Order status cannot be empty")
  private Double subTotal;
  @NotEmpty(message = "Discount cannot be empty")
  private Double discount;
  @NotEmpty(message = "Total cannot be empty")
  private Double total;
  @NotEmpty(message = "Payment method cannot be empty")
  private String paymentMethod;
  private String promotionCode;
  private String note;
  @NotEmpty(message = "Order items cannot be empty")
  List<OrderItemDTO> orderItemDTOs;
}