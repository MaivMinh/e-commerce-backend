package com.minh.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
  @Id
  private String id;
  private String userId;
  private String fullName;
  private String phone;
  private String address;
  private String ward;
  private String district;
  private String city;
  private Boolean isDefault;
}
