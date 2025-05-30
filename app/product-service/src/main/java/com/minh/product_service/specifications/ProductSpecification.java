package com.minh.product_service.specifications;

import com.minh.product_service.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
  public static Specification<Product> containsName(String name) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + name + "%");
      }
    };
  }

  public static Specification<Product> hasCategoryIds(List<String> categoryIds) {
    return (root, query, cb) -> categoryIds == null || categoryIds.isEmpty()
            ? cb.conjunction()
            : root.get("categoryId").in(categoryIds);
  }

  public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
    return (root, query, cb) -> {
      if (minPrice != null && maxPrice != null) {
        return cb.between(root.get("price"), minPrice, maxPrice);
      } else if (minPrice != null) {
        return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
      } else if (maxPrice != null) {
        return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
      }
      return cb.conjunction();
    };
  }
}