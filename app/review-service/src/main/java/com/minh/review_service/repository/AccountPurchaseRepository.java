package com.minh.review_service.repository;

import com.minh.review_service.entity.AccountPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountPurchaseRepository extends JpaRepository<AccountPurchase, String> {
  List<AccountPurchase> findByAccountIdAndProductId(String accountId, String productId);
}
