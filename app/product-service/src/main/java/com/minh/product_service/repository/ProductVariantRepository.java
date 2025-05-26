package com.minh.product_service.repository;

import com.minh.product_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
  List<ProductVariant> findByProductId(String productId);
}
