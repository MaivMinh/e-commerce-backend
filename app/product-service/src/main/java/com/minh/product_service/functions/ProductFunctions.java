package com.minh.product_service.functions;


import com.minh.product_service.dto.AccountWithProductInfoRequestDTO;
import com.minh.product_service.dto.AccountWithProductInfoResponseDTO;
import com.minh.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFunctions {
  private final ProductService productService;

  @Bean
  public Function<AccountWithProductInfoRequestDTO, AccountWithProductInfoResponseDTO> userWithProductInfo() {
    return accountWithProductInfoRequestDTO -> {
      log.info("Handling AccountWithProductInfoRequestDTO for accountId: {}", accountWithProductInfoRequestDTO.getAccountId());
      String productId = productService.findProductIdByProductVariantId(accountWithProductInfoRequestDTO.getProductVariantId());
      return AccountWithProductInfoResponseDTO.builder()
          .accountId(accountWithProductInfoRequestDTO.getAccountId())
          .productVariantId(accountWithProductInfoRequestDTO.getProductVariantId())
          .productId(productId)
          .build();
    };
  }

}
