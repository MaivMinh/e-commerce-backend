package com.minh.order_service.mapper;

import com.minh.order_service.DTOs.OrderDTO;
import com.minh.order_service.entity.OrderItem;
import com.minh.order_service.entity.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderItemMapper {
  public static void mapToOrderItemDTO(OrderItem orderItem, OrderItemDTO orderItemDTO) {
    if (orderItem == null) {
      log.warn("orderItem is null");
      return;
    }

    if (orderItemDTO == null) {
      orderItemDTO = new OrderItemDTO();
    }

    orderItemDTO.setId(orderItem.getId());
    orderItemDTO.setOrderId(orderItem.getOrderId());
    orderItemDTO.setQuantity(orderItem.getQuantity());
    orderItemDTO.setPrice(orderItem.getPrice());
    orderItemDTO.setTotal(orderItem.getTotal());
  }
}
