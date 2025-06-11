package com.minh.auth_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDTO {
  @NotEmpty(message = "Account ID cannot be empty")
  private String accountId;
  @NotEmpty(message = "Old password cannot be empty")
  private String oldPassword;
  @NotEmpty(message = "New password cannot be empty")
  private String newPassword;
  @NotEmpty(message = "Confirm new password cannot be empty")
  private String confirmNewPassword;
}
