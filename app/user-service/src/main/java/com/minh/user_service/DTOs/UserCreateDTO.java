package com.minh.user_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
  @NotEmpty(message = "Account ID cannot be empty")
  private String accountId;
  @NotEmpty(message = "Username cannot be empty")
  private String username;
  @NotEmpty(message = "Full name cannot be empty")
  private String fullName;
  private String avatar;
  private String gender;
  private Date birthDate;
}
