package com.minh.product_service.specifications;

import com.minh.product_service.entity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecs {
  public static Specification<Category> containsName(String name) {
    return new Specification<Category>() {
      @Override
      public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + name + "%");
      }
    };
  }

  public static Specification<Category> containsDescription(String description) {
    return new Specification<Category>() {
      @Override
      public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("description"), "%" + description + "%");
      }
    };
  }
}
