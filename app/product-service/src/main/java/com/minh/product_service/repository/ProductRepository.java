package com.minh.product_service.repository;

import com.minh.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
  Page<Product> findAll(Specification<Product> specification, Pageable pageable);
  Optional<Product> findProductBySlug(String slug);
  Page<Product> findProductByCategoryId(String categoryId, Pageable pageable);
}
