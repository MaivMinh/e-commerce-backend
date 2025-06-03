package com.minh.product_service.service;

import com.minh.common.dto.ReserveProductItem;
import com.minh.common.events.ProductReserveRollbackedEvent;
import com.minh.common.events.ProductReservedEvent;
import com.minh.product_service.entity.ProductVariant;
import com.minh.product_service.entity.ReserveProduct;
import com.minh.product_service.repository.ProductVariantRepository;
import com.minh.product_service.repository.ReserveProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveProductService {
  private final ReserveProductRepository reserveProductRepository;
  private final ProductVariantRepository productVariantRepository;

  /// Hàm thực hiện đặt chỗ sản phẩm.
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
  public void reserveProduct(ProductReservedEvent event) {
    /// Tạo đối tượng ReserveProduct từ orderId và reserveProductItems.
    String orderId = event.getOrderId();
    List<ReserveProductItem> reserveProductItems = event.getReserveProductItems();
    reserveProductItems.forEach(item -> {
      /// Deduct the quantity from the product variant stock.
      int quantity = item.getQuantity();
      String productVariantId = item.getProductVariantId();
      ProductVariant variant = productVariantRepository.findById(productVariantId).orElseThrow(() -> new RuntimeException("Product variant not found: " + productVariantId));
      if (variant.getQuantity() < quantity) {
        throw new RuntimeException("Not enough stock for product variant: " + productVariantId);
      }
      variant.setQuantity(variant.getQuantity() - quantity);
      productVariantRepository.save(variant);

      ReserveProduct reserve = new ReserveProduct();
      reserve.setId(UUID.randomUUID().toString());
      reserve.setOrderId(orderId);
      reserve.setProductVariantId(item.getProductVariantId());
      reserve.setQuantity(item.getQuantity());
      reserveProductRepository.save(reserve);
    });
  }

  /// Hàm thực hiện rollback đặt chỗ sản phẩm.
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
  public void rollbackReserveProduct(ProductReserveRollbackedEvent event) {
    String orderId = event.getOrderId();
    List<ReserveProduct> reserveProducts = reserveProductRepository.findAllByOrderId(orderId);
    if (reserveProducts.isEmpty()) {
      log.warn("No reserve products found for orderId: {}", orderId);
      throw new RuntimeException("No reserve products found for orderId: " + orderId);
    }

    reserveProducts.forEach(reserveProduct -> {
      /// Trả lại số lượng sản phẩm vào kho.
      ProductVariant variant = productVariantRepository.findById(reserveProduct.getProductVariantId())
              .orElseThrow(() -> new RuntimeException("Product variant not found: " + reserveProduct.getProductVariantId()));
      variant.setQuantity(variant.getQuantity() + reserveProduct.getQuantity());
      productVariantRepository.save(variant);
      /// Xoá bản ghi đặt chỗ sản phẩm.
      reserveProductRepository.delete(reserveProduct);
    });
  }
}