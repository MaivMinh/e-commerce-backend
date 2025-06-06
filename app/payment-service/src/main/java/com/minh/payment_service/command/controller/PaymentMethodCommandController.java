package com.minh.payment_service.command.controller;

import com.minh.payment_service.DTOs.PaymentMethodCreateDTO;
import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.command.commands.CreatePaymentMethodCommand;
import com.minh.payment_service.command.commands.DeletePaymentMethodCommand;
import com.minh.payment_service.command.commands.UpdatePaymentMethodCommand;
import com.minh.payment_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/payment-methods")
public class PaymentMethodCommandController {
  private final CommandGateway commandGateway;

  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createPaymentMethod(@RequestBody @Valid PaymentMethodCreateDTO paymentMethodCreateDTO) {
    CreatePaymentMethodCommand command = CreatePaymentMethodCommand.builder()
            .paymentMethodId(UUID.randomUUID().toString())
            .code(paymentMethodCreateDTO.getCode())
            .name(paymentMethodCreateDTO.getName())
            .description(paymentMethodCreateDTO.getDescription())
            .type(paymentMethodCreateDTO.getType())
            .provider(paymentMethodCreateDTO.getProvider())
            .iconUrl(paymentMethodCreateDTO.getIconUrl())
            .currency(paymentMethodCreateDTO.getCurrency())
            .isActive(paymentMethodCreateDTO.getIsActive())
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(ResponseData.builder().message("Payment method created successfully").build());
  }

  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updatePaymentMethod(@RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
    UpdatePaymentMethodCommand command = UpdatePaymentMethodCommand.builder()
            .paymentMethodId(paymentMethodDTO.getPaymentMethodId())
            .code(paymentMethodDTO.getCode())
            .name(paymentMethodDTO.getName())
            .description(paymentMethodDTO.getDescription())
            .type(paymentMethodDTO.getType())
            .provider(paymentMethodDTO.getProvider())
            .currency(paymentMethodDTO.getCurrency())
            .iconUrl(paymentMethodDTO.getIconUrl())
            .isActive(paymentMethodDTO.getIsActive())
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.OK.value()).body(ResponseData.builder().message("Payment method updated successfully").build());
  }


  @DeleteMapping(value = "/{paymentMethodId}")
  public ResponseEntity<ResponseData> deletePaymentMethod(@PathVariable String paymentMethodId) {
    DeletePaymentMethodCommand command = DeletePaymentMethodCommand.builder()
            .paymentMethodId(paymentMethodId)
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    // No response body needed for deletion, just return 204 No Content
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseData(204, "Payment method deleted successfully"));
  }
}
