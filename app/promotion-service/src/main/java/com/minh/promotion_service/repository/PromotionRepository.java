package com.minh.promotion_service.repository;

import com.minh.promotion_service.entity.Promotion;
import com.minh.promotion_service.entity.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
  List<Promotion> findAllByStatus(PromotionStatus status);
  Page<Promotion> findByStatusAndCodeContainingIgnoreCase(PromotionStatus promotionStatus, String code, Pageable pageable);

  Page<Promotion> findByStatus(PromotionStatus promotionStatus, Pageable pageable);

  Page<Promotion> findByCodeContainingIgnoreCase(String search, Pageable pageable);
}
