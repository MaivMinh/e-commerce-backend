package com.minh.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "account_addresses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAddress extends BaseEntity {
  @Id
  private String id;
  private String accountId;
  private String fullName;
  private String phone;
  private String address;
  private String ward;
  private String district;
  private String city;
  private String country;
}