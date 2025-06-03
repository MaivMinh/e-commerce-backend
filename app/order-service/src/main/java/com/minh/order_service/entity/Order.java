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
@Table(name = "orders")
public class Order extends BaseEntity {
  @Id
  private String id;
  private String accountId;
  private String shippingAddressId;
  private OrderStatus orderStatus;
  private Double subTotal;
  private Double discount;
  private Double total;
  private PaymentMethod paymentMethod;
  private PaymentStatus paymentStatus;
  private String promotionId;
  private String note;
}
