package com.minh.common.commands;

import com.minh.common.dto.OrderItemCreateDTO;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {
  @TargetAggregateIdentifier
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
