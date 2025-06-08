package com.minh.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "accounts")
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {
  @Id
  private String id;
  private String username;
  private String password;
  private String email;
  private String name;
  private Role role;
  private Status status;
  private String avatar;
}