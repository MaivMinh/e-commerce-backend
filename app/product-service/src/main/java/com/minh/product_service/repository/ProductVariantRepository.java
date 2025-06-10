package com.minh.product_service.repository;

import com.minh.product_service.dto.ProductCartDTO;
import com.minh.product_service.dto.ProductVariantMessageDTO;
import com.minh.product_service.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
  List<ProductVariant> findByProductId(String productId);

  @Query(value = "" +
          "select pvs.id as productVariantId, pvs.size as productVariantSize, pvs.color_name as productVariantColorName, pvs.color_hex as productVariantColorHex, pvs.price as productVariantPrice, pvs.original_price as productVariantOriginalPrice, P.name as productName, P.cover as productCover, P.slug as productSlug " +
          "from product_variants pvs join products P on pvs.product_id = P.id " +
          "where pvs.id = :productVariantId ",
          nativeQuery = true)
  Optional<ProductCartDTO> findProductCartDTOByProductVariantId(String productVariantId);

  @Query(value = "" +
          "select P.name as productName, P.slug as productSlug, P.cover as productCover, pvs.size as productVariantSize, pvs.color_name as productVariantColorName, pvs.color_hex as productVariantColorHex, pvs.price as productVariantPrice, pvs.original_price as productVariantOriginalPrice from product_variants pvs join products P on pvs.product_id = P.id " +
          "where pvs.id = :productVariantId", nativeQuery = true)
  ProductVariantMessageDTO findProductVariantById(String productVariantId);

  List<ProductVariant> findAllByProductId(String id);

  void deleteAllByProductId(String id);
}
