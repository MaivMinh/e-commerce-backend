package com.minh.payment_service.specification;

import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.entity.PaymentMethodType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PaymentMethodSpecification {
  public static Specification<PaymentMethod> containsName(String name) {
    return new Specification<PaymentMethod>() {
      @Override
      public Predicate toPredicate(Root<PaymentMethod> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + name + "%");
      }
    };
  }

  public static Specification<PaymentMethod> isActive(Boolean isActive) {
    return new Specification<PaymentMethod>() {
      @Override
      public Predicate toPredicate(Root<PaymentMethod> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("isActive"), isActive);
      }
    };
  }

  public static Specification<PaymentMethod> paymentMethodType(PaymentMethodType paymentMethodType) {
    return new Specification<PaymentMethod>() {
      @Override
      public Predicate toPredicate(Root<PaymentMethod> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("type"), paymentMethodType);
      }
    };
  }
}
