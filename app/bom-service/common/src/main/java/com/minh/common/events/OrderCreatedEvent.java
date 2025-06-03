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
  private String shippingAddressId;
  private Double subTotal;
  private Double discount;
  private Double total;
  private String paymentMethodId;
  private String promotionId;
  private String currency;
  private String note;
  private List<OrderItemCreateDTO> orderItemDTOs;
}
