package com.minh.order_service.functions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderFunctions {
  @Bean
  public Consumer<Boolean> handleOrderCreated() {
    return orderCreated -> {
      log.info("Received result of order: {}", orderCreated);
    };
  }
}
