package com.minh.review_service.service;

import com.minh.review_service.entity.AccountPurchase;
import com.minh.review_service.repository.AccountPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountPurchaseService {
  private final AccountPurchaseRepository accountPurchaseRepository;
  // Add methods to handle account purchases, such as saving, retrieving, etc.

  public AccountPurchase saveAccountPurchase(AccountPurchase accountPurchase) {
    return accountPurchaseRepository.save(accountPurchase);
  }

  public AccountPurchase findById(String id) {
    return accountPurchaseRepository.findById(id).orElse(null);
  }

  public void deleteById(String id) {
    accountPurchaseRepository.deleteById(id);
  }
}
