package com.minh.order_service.service;

import ch.qos.logback.core.util.StringUtil;
import com.minh.common.dto.OrderItemCreateDTO;
import com.minh.common.events.CreateOrderConfirmedEvent;
import com.minh.common.events.OrderCreateRollbackedEvent;
import com.minh.common.events.OrderCreatedEvent;
import com.minh.grpc_service.product.FindProductVariantByIdRequest;
import com.minh.grpc_service.product.FindProductVariantByIdResponse;
import com.minh.grpc_service.product.ProductVariant;
import com.minh.grpc_service.user.GetUserInfoRequest;
import com.minh.grpc_service.user.GetUserInfoResponse;
import com.minh.order_service.DTOs.OrderDTO;
import com.minh.order_service.DTOs.ProductVariantDTO;
import com.minh.order_service.entity.*;
import com.minh.order_service.grpc.ProductServiceGrpcClient;
import com.minh.order_service.grpc.UserServiceGrpcClient;
import com.minh.order_service.mapper.OrderItemMapper;
import com.minh.order_service.mapper.OrderMapper;
import com.minh.order_service.query.queries.FindAllOrdersQuery;
import com.minh.order_service.query.queries.FindOrdersOfAccountQuery;
import com.minh.order_service.query.queries.FindOverallStatusOfCreatingOrderQuery;
import com.minh.order_service.repository.OrderItemRepository;
import com.minh.order_service.repository.OrderRepository;
import com.minh.order_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserServiceGrpcClient userServiceGrpcClient;
  private final ProductServiceGrpcClient productServiceGrpcClient;

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

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
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

  public void confirmCreateOrder(CreateOrderConfirmedEvent event) {
    /// Hàm xác nhận việc tạo đơn hàng thành công.
    Order order = orderRepository.findById(event.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found for id: " + event.getOrderId()));
    order.setOrderStatus(OrderStatus.completed);
    order.setPaymentStatus(PaymentStatus.completed);
    orderRepository.save(order);
  }



  /// ========================== APPLY TEMPLATE METHOD DESIGN PATTERN HERE  ==========================  ///
  public ResponseData findOverallStatusOfCreatingOrder(FindOverallStatusOfCreatingOrderQuery query) {
    Order order = orderRepository.findById(query.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found for id: " + query.getOrderId()));
    return ResponseData.builder()
            .status(200)
            .message("Order status retrieved successfully")
            .data(Collections.singletonMap("orderStatus", order.getOrderStatus()))
            .build();
  }

  public ResponseData findAllOrders(FindAllOrdersQuery query) throws Exception {
    int page = query.getPage();
    int size = query.getSize();
    return processOrderQuery(page, size, null, "Orders retrieved successfully", "No orders found");
  }


  public ResponseData findOrdersOfAccount(FindOrdersOfAccountQuery query) throws Exception {
    int page = query.getPage();
    int size = query.getSize();
    String accountId = query.getAccountId();
    return processOrderQuery(page, size, accountId, "Orders for account retrieved successfully",
            "No orders found for this account");
  }

  /**
   * Template method to process order queries with or without account filtering
   */
  private ResponseData processOrderQuery(int page, int size, String accountId, String successMessage,
                                         String emptyMessage) throws Exception {
    Pageable pageable = PageRequest.of(page, size);
    Page<Order> pageOrder = fetchOrders(pageable, accountId);

    if (pageOrder.isEmpty()) {
      return ResponseData.builder()
              .status(200)
              .message(emptyMessage)
              .data(null)
              .build();
    }

    List<Order> orders = pageOrder.getContent();
    List<OrderDTO> orderDTOs = convertOrdersToDto(orders);

    Map<String, Object> data = new HashMap<>();
    data.put("orders", orderDTOs);
    data.put("totalElements", pageOrder.getTotalElements());
    data.put("totalPages", pageOrder.getTotalPages());
    data.put("page", pageOrder.getNumber() + 1);
    data.put("size", pageOrder.getSize());

    return ResponseData.builder()
            .status(200)
            .message(successMessage)
            .data(data)
            .build();
  }

  /**
   * Fetch orders based on whether an accountId is provided
   */
  private Page<Order> fetchOrders(Pageable pageable, String accountId) {
    if (accountId != null && !accountId.isEmpty()) {
      return orderRepository.findAllByAccountId(accountId, pageable);
    } else {
      return orderRepository.findAll(pageable);
    }
  }

  /**
   * Convert order entities to DTOs with related data
   */
  private List<OrderDTO> convertOrdersToDto(List<Order> orders) throws Exception {
    List<OrderDTO> orderDTOs = new ArrayList<>();

    for (Order order : orders) {
      OrderDTO orderDTO = new OrderDTO();
      OrderMapper.mapToOrderDTO(order, orderDTO);

      // Get user information
      GetUserInfoRequest request = GetUserInfoRequest.newBuilder()
              .setAccountId(order.getAccountId())
              .setShippingAddressId(order.getShippingAddressId())
              .build();
      GetUserInfoResponse userInfoResponse = userServiceGrpcClient.getUserInfo(request);

      orderDTO.setCreatedAt(order.getCreatedAt());
      orderDTO.setUsername(userInfoResponse.getUsername());
      orderDTO.setFullName(userInfoResponse.getFullName());
      orderDTO.setShippingAddress(userInfoResponse.getShippingAddress());

      // Get order items
      List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
      List<OrderItemDTO> orderItemDTOs = orderItems.stream().map(this::convertOrderItemToDto)
              .collect(Collectors.toList());

      orderDTO.setOrderItemDTOs(orderItemDTOs);
      orderDTOs.add(orderDTO);
    }

    return orderDTOs;
  }

  /**
   * Convert order item entity to DTO with product variant information
   */
  private OrderItemDTO convertOrderItemToDto(OrderItem item) {
    OrderItemDTO orderItemDTO = new OrderItemDTO();
    OrderItemMapper.mapToOrderItemDTO(item, orderItemDTO);

    try {
      FindProductVariantByIdResponse response = productServiceGrpcClient.findProductVariantById(
              FindProductVariantByIdRequest.newBuilder()
                      .setProductVariantId(item.getProductVariantId())
                      .build());

      if (response.hasProductVariant()) {
        ProductVariant productVariant = response.getProductVariant();
        ProductVariantDTO productVariantDTO = new ProductVariantDTO();
        productVariantDTO.setId(productVariant.getId());
        productVariantDTO.setName(productVariant.getName());
        productVariantDTO.setPrice(productVariant.getPrice());
        productVariantDTO.setColorName(productVariant.getColorName());
        productVariantDTO.setSize(productVariant.getSize());
        productVariantDTO.setQuantity(item.getQuantity());
        productVariantDTO.setCover(productVariant.getCover());
        orderItemDTO.setProductVariantDTO(productVariantDTO);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error fetching product variant: " + e.getMessage(), e);
    }

    return orderItemDTO;
  }
}