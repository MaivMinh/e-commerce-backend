package com.minh.product_service.repository;

import com.minh.product_service.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  Page<Category> findAll(Specification<Category> specification, Pageable pageable);
  Page<Category> findByNameContaining(String name, Pageable pageable);
}
