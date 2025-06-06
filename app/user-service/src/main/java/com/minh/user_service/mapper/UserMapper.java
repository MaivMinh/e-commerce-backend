package com.minh.user_service.mapper;

import com.minh.user_service.DTOs.UserDTO;
import com.minh.user_service.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMapper {
  public static void mapToUserDTO(User user, UserDTO userDTO) {
    if (user == null) {
      log.warn("User object is null, cannot map to UserDTO");
      return;
    }
    if (userDTO == null) {
      log.warn("UserDTO object is null, creating a new UserDTO instance");
      userDTO = new UserDTO();
    }
    userDTO.setId(user.getId());
    userDTO.setUsername(user.getUsername());
    userDTO.setFullName(user.getFullName());
    userDTO.setAvatar(user.getAvatar());
    userDTO.setGender(user.getGender().toString());
    userDTO.setBirthDate(user.getBirthDate());
    userDTO.setCreatedAt(user.getCreatedAt());
  }
}
