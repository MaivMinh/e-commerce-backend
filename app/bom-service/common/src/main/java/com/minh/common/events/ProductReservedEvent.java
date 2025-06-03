package com.minh.common.events;


import com.minh.common.dto.ReserveProductItem;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductReservedEvent {
  private String reserveProductId;
  private String orderId;
  private List<ReserveProductItem> reserveProductItems;
  private String promotionId;
  private String accountId;
}