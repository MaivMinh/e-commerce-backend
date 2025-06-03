package com.minh.auth_service.repository;

import com.minh.auth_service.model.AccountAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountAddressRepository extends JpaRepository<AccountAddress, String> {
  List<AccountAddress> findAllByAccountId(String accountId);
}
