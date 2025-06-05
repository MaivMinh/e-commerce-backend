package com.minh.user_service.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
  private String id;
  private String fullName;
  private String phone;
  private String address;
  private String ward;
  private String district;
  private String city;
  private Boolean isDefault;
}
