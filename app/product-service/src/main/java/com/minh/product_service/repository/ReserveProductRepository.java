package com.minh.product_service.repository;

import com.minh.product_service.entity.ReserveProduct;
import com.minh.product_service.service.ReserveProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveProductRepository extends JpaRepository<ReserveProduct, String> {
}
