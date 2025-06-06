package com.minh.payment_service.repository;

import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.entity.PaymentMethodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
  List<PaymentMethod> findAllByType(PaymentMethodType type);

  Page<PaymentMethod> findAll(Specification<PaymentMethod> specification, Pageable pageable);
}
