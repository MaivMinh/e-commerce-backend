package com.minh.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
  @Id
  private String id;
  private String accountId;
  private String username;
  private String fullName;
  private String avatar;
  private Gender gender;
  private Date birthDate;
}
