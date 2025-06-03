package com.minh.auth_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAddressDTO {
  private String id;
  @NotEmpty(message = "Account ID cannot be empty")
  private String accountId;
  @NotEmpty(message = "Full name cannot be empty")
  private String fullName;
  @NotEmpty(message = "Phone number cannot be empty")
  private String phone;
  @NotEmpty(message = "Address cannot be empty")
  private String address;
  private String ward;
  private String district;
  private String city;
  private String country;
}
