package com.minh.payment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {
  @Id
  private String id;
  private String orderId;
  private String paymentMethodId;
  private Double amount;
  private PaymentCurrency currency;
  private PaymentStatus status;
  private String transactionId;
  private Timestamp paymentDate;
}
