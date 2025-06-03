package com.minh.promotion_service.repository;

import com.minh.promotion_service.entity.OrderPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, String> {
}
