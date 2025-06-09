package com.minh.user_service.adapter;

import com.minh.user_service.DTOs.AccountCreatedMessageDTO;
import com.minh.user_service.DTOs.ActiveUserMessageDTO;
import com.minh.user_service.DTOs.InactiveUserMessageDTO;
import com.minh.user_service.DTOs.UserCreateDTO;
import com.minh.user_service.entity.User;
import com.minh.user_service.service.IUserFunctions;
import com.minh.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateUserAdapter implements IUserFunctions {
  private final UserService userService;

  @Override
  public Boolean createUserFunction(AccountCreatedMessageDTO accountCreatedMessageDTO) {
    try {
      /// Convert AccountCreatedMessageDTO to UserCreateDTO object.
      UserCreateDTO userCreateDTO = new UserCreateDTO();
      userCreateDTO.setAccountId(accountCreatedMessageDTO.accountId());
      userCreateDTO.setUsername(accountCreatedMessageDTO.username());
      userCreateDTO.setEmail(accountCreatedMessageDTO.email());
      userCreateDTO.setFullName(accountCreatedMessageDTO.fullName());
      /// Call createUserFunction method from UserService.
      userService.createUser(userCreateDTO);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Boolean inactiveUserFunction(InactiveUserMessageDTO inactiveUserMessageDTO) {
    try {
      User user = userService.findUserByAccountId(inactiveUserMessageDTO.accountId());
      userService.inactiveUser(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Boolean activeUserFunction(ActiveUserMessageDTO activeUserMessageDTO) {
    try {
      User user = userService.findUserByAccountId(activeUserMessageDTO.accountId());
      userService.activeUser(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
