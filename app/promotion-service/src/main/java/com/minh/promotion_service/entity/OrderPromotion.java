package com.minh.promotion_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "order_promotions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPromotion extends BaseEntity {
  @Id
  private String id;
  private String orderId;
  private String promotionId;
  private Boolean isUsed;
  private Timestamp usedAt;
}
