package com.minh.common.events;

import com.minh.common.dto.OrderItemCreateDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
  private String orderId;
  private String accountId;
  private String shippingAddress;
  private String orderStatus;
  private Double subTotal;
  private Double discount;
  private Double total;
  private String paymentMethod;
  private String paymentStatus;
  private String promotionId;
  private String note;
  private List<OrderItemCreateDTO> orderItemDTOs;
}
