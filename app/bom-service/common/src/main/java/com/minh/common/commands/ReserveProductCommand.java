package com.minh.common.commands;

import com.minh.common.dto.ReserveProductItem;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReserveProductCommand {
  @TargetAggregateIdentifier
  private String reserveProductId;
  private String orderId;
  private String promotionId;
  private String paymentMethodId;
  private Double amount;
  private String currency;
  private List<ReserveProductItem> reserveProductItems;
  private String errorMsg;
}