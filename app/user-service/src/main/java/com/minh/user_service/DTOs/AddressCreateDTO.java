package com.minh.user_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateDTO {
  @NotEmpty(message = "User ID cannot be empty")
  private String userId;
  @NotEmpty(message = "Full name cannot be empty")
  private String fullName;
  @NotEmpty(message = "Phone number cannot be empty")
  private String phone;
  @NotEmpty(message = "Address cannot be empty")
  private String address;
  private String ward;
  private String district;
  private String city;
  @NotNull(message = "Default address status cannot be null")
  private Boolean isDefault;
}
