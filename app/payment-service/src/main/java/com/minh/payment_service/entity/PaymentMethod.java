package com.minh.payment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod extends BaseEntity {
  @Id
  private String id;
  private String code;
  private String name;
  private String description;
  private PaymentMethodType type;
  private String provider;
  private PaymentCurrency currency;
  private String iconUrl;
  private Boolean isActive;
}