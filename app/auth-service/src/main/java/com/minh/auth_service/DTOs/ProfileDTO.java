package com.minh.auth_service.DTOs;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
  private String id;
  private String username;
  private String email;
  private String name;
  private String role;
  private String avatar;
  List<AccountAddressDTO> addressDTOs;
}
