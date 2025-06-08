package com.minh.user_service.functions;

import com.minh.user_service.DTOs.AccountCreatedMessageDTO;
import com.minh.user_service.DTOs.ActiveUserMessageDTO;
import com.minh.user_service.DTOs.InactiveUserMessageDTO;
import com.minh.user_service.service.IAccountFunctions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserFunctions {
  private final IAccountFunctions iAccountFunctions;

  @Bean
  public Function<AccountCreatedMessageDTO, Boolean> createUserFunction() {
    return accountCreatedMessageDTO -> {
      try {
        log.info("Received message from Kafka topic 'account-created': {}", accountCreatedMessageDTO);
        return iAccountFunctions.createUserFunction(accountCreatedMessageDTO);
      } catch (Exception e) {
        log.error("Error processing account creation message: {}", e.getMessage(), e);
        return false;
      }
    };
  }

  @Bean
  public Function<InactiveUserMessageDTO, Boolean> inactiveUserFunction() {
    return inactiveUserMessageDTO -> {
      try {
        log.info("Received message from Kafka topic 'inactive-user': {}", inactiveUserMessageDTO);
        // Logic to deactivate user goes here
        return iAccountFunctions.inactiveUserFunction(inactiveUserMessageDTO);
      } catch (Exception e) {
        log.error("Error processing user deactivation message: {}", e.getMessage(), e);
        return false; // Return false if there was an error
      }
    };
  }

  @Bean
  public Function<ActiveUserMessageDTO, Boolean> activeUserFunction() {
    return activeUserMessageDTO -> {
      try {
        log.info("Received message from Kafka topic 'inactive-user': {}", activeUserMessageDTO);
        // Logic to deactivate user goes here
        return iAccountFunctions.activeUserFunction(activeUserMessageDTO);
      } catch (Exception e) {
        log.error("Error processing user deactivation message: {}", e.getMessage(), e);
        return false; // Return false if there was an error
      }
    };
  }
}