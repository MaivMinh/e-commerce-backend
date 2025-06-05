package com.minh.user_service.DTOs;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private String id;
  private String username;
  private String fullName;
  private String avatar;
  private String gender;
  private Date birthDate;
  private List<AddressDTO> addressDTOs;
}
