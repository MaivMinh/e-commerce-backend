package com.minh.review_service.functions;

import com.minh.review_service.DTOs.AccountWithProductInfoDTO;
import com.minh.review_service.DTOs.OrderCreatedMessageDTO;
import com.minh.review_service.DTOs.RetrievedProductInfoDTO;
import com.minh.review_service.entity.AccountPurchase;
import com.minh.review_service.service.ICreateNewAccountPurchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewFunctions {
  private final StreamBridge streamBridge;
  private final ICreateNewAccountPurchase iCreateNewAccountPurchase;

  @Bean
  public Function<OrderCreatedMessageDTO, Boolean> handleOrderCreated() {
    return orderCreatedMessageDTO -> {
      log.info("Handling OrderCreatedMessageDTO for accountId: {}", orderCreatedMessageDTO.getAccountId());
      for (String productVariantId : orderCreatedMessageDTO.getProductVariantIds()) {
        /// Gọi tới product service để lấy thông tin sản phẩm
        log.info("Processing productVariantId: {}", productVariantId);
        AccountWithProductInfoDTO userWithProductInfoDTO = AccountWithProductInfoDTO.builder()
                .accountId(orderCreatedMessageDTO.getAccountId())
                .productVariantId(productVariantId)
                .build();
        var result = streamBridge.send("userWithProductInfo-out-0", userWithProductInfoDTO);
        if (result) {
          log.info("Send event to userWithProductInfo-out-0 successfully for productVariantId: {}", productVariantId);
        } else {
          log.error("Failed to send event to userWithProductInfo-out-0 for productVariantId: {}", productVariantId);
        }
      }
      return true;
    };
  }


  @Bean
  public Consumer<RetrievedProductInfoDTO> handleRetrievedProductInfo() {
    /// Hàm xử lý khi nhận được thông tin sản phẩm từ product service
    return retrievedProductInfoDTO -> {
      log.info("Handling RetrievedProductInfoDTO for accountId: {}, productVariantId: {}, productId: {}",
              retrievedProductInfoDTO.getAccountId(),
              retrievedProductInfoDTO.getProductVariantId(),
              retrievedProductInfoDTO.getProductId());

      /// Lưu dữ liệu vào cơ sở dữ liệu hoặc thực hiện các hành động khác
      boolean result = iCreateNewAccountPurchase.createNewAccountPurchase(retrievedProductInfoDTO);
      if (result) {
        log.info("Successfully created new account purchase for accountId: {}, productId: {}",
                retrievedProductInfoDTO.getAccountId(),
                retrievedProductInfoDTO.getProductId());
      } else {
        log.error("Failed to create new account purchase for accountId: {}, productId: {}",
                retrievedProductInfoDTO.getAccountId(),
                retrievedProductInfoDTO.getProductId());
      }
    };
  }
}