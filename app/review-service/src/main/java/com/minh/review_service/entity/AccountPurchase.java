package com.minh.review_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account_purchases")
public class AccountPurchase extends BaseEntity {
  @Id
  private String id;
  private String accountId;
  private String productId;
}
