package com.minh.common.events;

import com.minh.common.dto.ReserveProductItem;

import java.util.List;

public class PromotionAppliedEvent {
  private String orderId;
  private List<ReserveProductItem> reserveProductItems;
  private String promotionId;
  private String accountId;
}
