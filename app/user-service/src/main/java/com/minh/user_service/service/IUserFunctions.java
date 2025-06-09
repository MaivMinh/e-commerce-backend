package com.minh.user_service.service;

import com.minh.user_service.DTOs.AccountCreatedMessageDTO;
import com.minh.user_service.DTOs.ActiveUserMessageDTO;
import com.minh.user_service.DTOs.InactiveUserMessageDTO;

public interface IUserFunctions {
  Boolean createUserFunction(AccountCreatedMessageDTO accountCreatedMessageDTO);
  Boolean inactiveUserFunction(InactiveUserMessageDTO inactiveUserMessageDTO);
  Boolean activeUserFunction(ActiveUserMessageDTO activeUserMessageDTO);
}