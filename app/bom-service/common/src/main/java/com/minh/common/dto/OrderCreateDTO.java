package com.minh.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
  private String orderId;
  @NotEmpty(message = "Account ID cannot be empty")
  private String accountId;
  @NotEmpty(message = "Shipping address cannot be empty")
  private String shippingAddress;
  private Double subTotal;
  private Double discount;
  private Double total;
  @NotEmpty(message = "Payment method cannot be empty")
  private String paymentMethodId;
  private String promotionId;
  private String currency;
  private String note;
  List<OrderItemCreateDTO> orderItemDTOs;
}