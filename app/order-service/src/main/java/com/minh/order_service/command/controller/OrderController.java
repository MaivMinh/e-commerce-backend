package com.minh.order_service.command.controller;

import com.minh.common.commands.CreateOrderCommand;
import com.minh.common.dto.OrderCreateDTO;
import com.minh.common.dto.OrderItemCreateDTO;
import com.minh.order_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final CommandGateway commandGateway;

  /// Hàm thực hiện tạo đơn hàng.
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
    String orderId = UUID.randomUUID().toString();
    CreateOrderCommand command = CreateOrderCommand.builder()
            .orderId(orderId)
            .accountId(orderCreateDTO.getAccountId())
            .shippingAddress(orderCreateDTO.getShippingAddress())
            .subTotal(orderCreateDTO.getSubTotal())
            .discount(orderCreateDTO.getDiscount())
            .total(orderCreateDTO.getTotal())
            .paymentMethodId(orderCreateDTO.getPaymentMethodId())
            .currency(orderCreateDTO.getCurrency())
            .note(orderCreateDTO.getNote())
            .promotionId(orderCreateDTO.getPromotionId())
            .orderItemDTOs(orderCreateDTO.getOrderItemDTOs().stream().map(item -> OrderItemCreateDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .orderId(orderId)
                    .productVariantId(item.getProductVariantId())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .total(item.getTotal())
                    .build()).collect(Collectors.toList()))
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);

    return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseData(HttpStatus.CREATED.value(), "Success", null));
  }
}
