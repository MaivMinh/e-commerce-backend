package com.minh.product_service.service;

import com.minh.common.dto.ReserveProductItem;
import com.minh.product_service.entity.ReserveProduct;
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

  /// Hàm thực hiện đặt chỗ sản phẩm.
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void reserveProduct(String orderId, List<ReserveProductItem> reserveProductItems) {
    /// Tạo đối tượng ReserveProduct từ orderId và reserveProductItems.
    reserveProductItems.forEach(item -> {
      ReserveProduct reserve = new ReserveProduct();
      reserve.setId(UUID.randomUUID().toString());
      reserve.setOrderId(orderId);
      reserve.setProductVariantId(item.getProductVariantId());
      reserve.setQuantity(item.getQuantity());
      reserveProductRepository.save(reserve);
    });
  }
}