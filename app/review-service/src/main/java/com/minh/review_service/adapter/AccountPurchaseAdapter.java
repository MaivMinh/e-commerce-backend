package com.minh.review_service.adapter;

import com.minh.review_service.DTOs.RetrievedProductInfoDTO;
import com.minh.review_service.entity.AccountPurchase;
import com.minh.review_service.service.AccountPurchaseService;
import com.minh.review_service.service.ICreateNewAccountPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountPurchaseAdapter implements ICreateNewAccountPurchase {
  private final AccountPurchaseService accountPurchaseService;

  @Override
  public boolean createNewAccountPurchase(RetrievedProductInfoDTO retrievedProductInfoDTO) {
    /// Hàm thực hiện tạo mới một bản ghi mua hàng cho tài khoản
    try {
      AccountPurchase accountPurchase = new AccountPurchase();
      accountPurchase.setId(UUID.randomUUID().toString());
      accountPurchase.setAccountId(retrievedProductInfoDTO.getAccountId());
      accountPurchase.setProductId(retrievedProductInfoDTO.getProductId());
      accountPurchaseService.saveAccountPurchase(accountPurchase);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
