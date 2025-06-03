package com.minh.promotion_service.repository;

import com.minh.promotion_service.entity.OrderPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, String> {
  Optional<OrderPromotion> findByOrderId(String orderId);
}
