package com.minh.order_service.command.controller;

import com.minh.common.commands.CreateOrderCommand;
import com.minh.common.dto.OrderCreateDTO;
import com.minh.common.dto.OrderItemCreateDTO;
import com.minh.order_service.query.queries.FindOverallStatusOfCreatingOrderQuery;
import com.minh.order_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/api/orders")
@RequiredArgsConstructor
public class OrderCommandController {
  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  /// Hàm thực hiện tạo đơn hàng.
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
    String orderId = UUID.randomUUID().toString();
    CreateOrderCommand command = CreateOrderCommand.builder()
            .orderId(orderId)
            .accountId(orderCreateDTO.getAccountId())
            .shippingAddressId(orderCreateDTO.getShippingAddressId())
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

    try (SubscriptionQueryResult<ResponseData, ResponseData> queryResult = queryGateway.subscriptionQuery(new FindOverallStatusOfCreatingOrderQuery(), ResponseData.class, ResponseData.class);) {
      commandGateway.send(command, new CommandCallback<>() {
        @Override
        public void onResult(@Nonnull CommandMessage<? extends CreateOrderCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
          if (commandResultMessage.isExceptional()) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .body(new ResponseData(500, "Failed to create order: " + commandResultMessage.exceptionResult().getMessage()));
          }
        }
      });
      // Store the first update to avoid multiple blocking calls
      ResponseData responseData = queryResult.updates().blockFirst(Duration.ofSeconds(15));
      if (responseData == null) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Timeout waiting for order creation"));
      }
      return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
  }
}