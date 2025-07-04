package com.minh.order_service.repository;

import com.minh.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
  Page<Order> findAllByAccountId(String accountId, Pageable pageable);
}
