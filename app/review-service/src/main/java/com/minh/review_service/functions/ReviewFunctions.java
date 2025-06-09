package com.minh.review_service.functions;

import com.minh.review_service.DTOs.OrderCreatedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewFunctions {

  @Bean
  public Function<OrderCreatedDTO, Boolean> handleOrderCreated() {
    return orderCreatedDTO -> {
      log.info("Handling OrderCreatedDTO for accountId: {}", orderCreatedDTO.getAccountId());
      for (String productVariantId : orderCreatedDTO.getProductVariantIds()) {
        log.info("Processing productVariantId: {}", productVariantId);
        // Here you can add logic to create a review for the product variant
        // For example, you might want to save it to a database or send it to another service
      }
      return true;
    };
  }
}
