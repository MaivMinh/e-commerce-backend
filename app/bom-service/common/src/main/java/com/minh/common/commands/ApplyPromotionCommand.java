package com.minh.common.commands;

import com.minh.common.dto.ReserveProductItem;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.List;

public class ApplyPromotionCommand {
  @TargetAggregateIdentifier
  private String orderId;
  private List<ReserveProductItem> reserveProductItems;
  private String promotionId;
  private String accountId;
}
