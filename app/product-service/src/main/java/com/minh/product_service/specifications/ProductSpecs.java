package com.minh.product_service.specifications;

import com.minh.product_service.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
  public static Specification<Product> containsName(String name) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + name + "%");
      }
    };
  }

  public static Specification<Product> containsDescription(String description) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("description"), "%" + description + "%");
      }
    };
  }
}
