package com.minh.auth_service.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AccountFunctions {

  @Bean
  public Consumer<Boolean> confirmUserCreatedFunction() {
    return isCreated -> {
      if (isCreated) {
        log.info("User creation confirmed successfully.");
      } else {
        log.warn("User creation confirmation failed.");
      }
    };
  }

  @Bean
  public Consumer<Boolean> confirmUserInactivatedFunction() {
    return isInactivated -> {
      if (isInactivated) {
        log.info("User inactivation confirmed successfully.");
      } else {
        log.warn("User inactivation confirmation failed.");
      }
    };
  }

  @Bean
  public Consumer<Boolean> confirmUserActivatedFunction() {
    return isActivated -> {
      if (isActivated) {
        log.info("User activation confirmed successfully.");
      } else {
        log.warn("User activation confirmation failed.");
      }
    };
  }
}
