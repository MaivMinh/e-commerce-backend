package com.minh.auth_service.repository;

import com.minh.auth_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
  @Query(value = "select * from accounts where username = :username or email = :email", nativeQuery = true)
  Optional<Account> checkWhetherAccountIsAlreadyExistsOrNot(String username, String email);

  Optional<Account> findAccountByEmail(String email);

  Optional<Account> findAccountByUsername(String username);

  Optional<Account> findAccountById(String accountId);
}