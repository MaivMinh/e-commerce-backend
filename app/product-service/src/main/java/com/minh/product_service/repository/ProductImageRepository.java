package com.minh.product_service.repository;

import com.minh.product_service.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

  @Query(value = "select url from product_images where product_id = :productId", nativeQuery = true)
  Collection<String> findImagesByProductId(String productId);
}
