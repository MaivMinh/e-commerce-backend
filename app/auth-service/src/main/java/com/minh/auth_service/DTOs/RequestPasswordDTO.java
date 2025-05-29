package com.minh.auth_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPasswordDTO {
  @NotEmpty(message = "Email is required")
  private String newPassword;
}
