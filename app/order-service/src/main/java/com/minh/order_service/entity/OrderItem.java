package com.minh.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
  @Id
  private String id;
  private String orderId;
  private String productVariantId;
  private Integer quantity;
  private Double price;
  private Double total;
  private OrderItemStatus orderItemStatus;
}