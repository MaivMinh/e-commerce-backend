package com.minh.auth_service.services;

import com.minh.auth_service.model.Account;
import com.minh.auth_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;

  public Account findAccountByUsername(String username) {
    return accountRepository.findAccountByUsername(username).orElse(null);
  }

}