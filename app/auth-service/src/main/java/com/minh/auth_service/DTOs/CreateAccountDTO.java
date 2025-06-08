package com.minh.auth_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
  private String id;
  @NotEmpty(message = "Username cannot be empty")
  private String username;
  @NotEmpty(message = "Password cannot be empty")
  private String password;
  @NotEmpty(message = "Email cannot be empty")
  private String email;
  @NotEmpty(message = "Name cannot be empty")
  private String name;
  private String role;
  private String status;
  private String avatar;
}
