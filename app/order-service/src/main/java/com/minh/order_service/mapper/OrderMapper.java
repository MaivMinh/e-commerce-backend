package com.minh.order_service.mapper;

import com.minh.order_service.DTOs.OrderDTO;
import com.minh.order_service.entity.Order;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderMapper {
  public static void mapToOrderDTO(Order order, OrderDTO orderDTO) {
    if (order == null) {
      log.warn("Order is null");
      return;
    }
    if (orderDTO == null) {
      orderDTO = new OrderDTO();
    }
    orderDTO.setId(order.getId());
    orderDTO.setAccountId(order.getAccountId());
    orderDTO.setSubTotal(order.getSubTotal());
    orderDTO.setDiscount(order.getDiscount());
    orderDTO.setTotal(order.getTotal());
    orderDTO.setNote(order.getNote());
    orderDTO.setStatus(order.getOrderStatus().toString());
    orderDTO.setPaymentStatus(order.getPaymentStatus().toString());
  }
}
