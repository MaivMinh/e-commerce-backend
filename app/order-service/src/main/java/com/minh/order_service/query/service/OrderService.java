package com.minh.order_service.query.service;

import com.minh.common.dto.OrderItemCreateDTO;
import com.minh.common.events.OrderCreateRollbackedEvent;
import com.minh.common.events.OrderCreatedEvent;
import com.minh.order_service.entity.*;
import com.minh.order_service.repository.OrderItemRepository;
import com.minh.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  /// Hàm thực hiện tạo đơn hàng với các sản phẩm trong đơn hàng.
  public void createOrder(OrderCreatedEvent event) {
    Order order = new Order();
    order.setId(event.getOrderId());
    order.setAccountId(event.getAccountId());
    order.setShippingAddressId(event.getShippingAddressId());
    order.setOrderStatus(OrderStatus.pending);
    order.setSubTotal(event.getSubTotal());
    order.setDiscount(event.getDiscount());
    order.setTotal(event.getTotal());
    order.setPaymentMethodId(event.getPaymentMethodId());
    order.setPaymentStatus(PaymentStatus.pending);
    order.setPromotionId(event.getPromotionId());
    order.setNote(event.getNote());
    Order saved = orderRepository.save(order);

    List<OrderItemCreateDTO> orderItemDTOs = event.getOrderItemDTOs();
    orderItemDTOs.forEach(orderItemDTO -> {
      OrderItem orderItem = new OrderItem();
      orderItem.setId(orderItemDTO.getId());
      orderItem.setOrderId(saved.getId());
      orderItem.setProductVariantId(orderItemDTO.getProductVariantId());
      orderItem.setQuantity(orderItemDTO.getQuantity());
      orderItem.setPrice(orderItemDTO.getPrice());
      orderItem.setTotal(orderItemDTO.getTotal());
      log.info("Adding OrderItem with orderItemId: {}", orderItem.getId());
      orderItemRepository.save(orderItem);
    });
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rollbackOrderCreated(OrderCreateRollbackedEvent event) {
    /// Hàm thực hiện rollback đơn hàng khi có lỗi xảy ra trong quá trình tạo đơn hàng.
    Order order = orderRepository.findById(event.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found for id: " + event.getOrderId()));

    order.setPaymentStatus(PaymentStatus.failed);
    order.setOrderStatus(OrderStatus.failed);
    order.setPromotionId(null);
    order = orderRepository.save(order);

    /// Xoá các sản phẩm trong đơn hàng.
    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
    if (orderItems != null && !orderItems.isEmpty()) {
      orderItemRepository.deleteAll(orderItems);
    }
  }
}
