package com.minh.auth_service.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDTO {
  private String oldPassword;
  private String newPassword;
  private String confirmNewPassword;
}
