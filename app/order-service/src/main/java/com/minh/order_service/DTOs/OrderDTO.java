package com.minh.order_service.DTOs;

import com.minh.order_service.entity.OrderItemDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Timestamp;
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
  private String shippingAddress;
  @NotEmpty(message = "Order status cannot be empty")
  private Double subTotal;
  @NotEmpty(message = "Discount cannot be empty")
  private Double discount;
  @NotEmpty(message = "Total cannot be empty")
  private Double total;
  private String note;
  @NotEmpty(message = "Order items cannot be empty")
  List<OrderItemDTO> orderItemDTOs;
  private String status;
  private String paymentStatus;
  private String username;
  private String fullName;
  private Timestamp createdAt;
}