package com.minh.payment_service.service;

import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.command.events.PaymentMethodCreatedEvent;
import com.minh.payment_service.command.events.PaymentMethodDeletedEvent;
import com.minh.payment_service.command.events.PaymentMethodUpdatedEvent;
import com.minh.payment_service.entity.PaymentCurrency;
import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.entity.PaymentMethodType;
import com.minh.payment_service.mapper.PaymentMethodMapper;
import com.minh.payment_service.query.queries.FindPaymentMethodsByTypeQuery;
import com.minh.payment_service.repository.PaymentMethodRepository;
import com.minh.payment_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;

  public void createPaymentMethod(PaymentMethodCreatedEvent event) {
    // Create a new payment method entity from the event
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setId(event.getPaymentMethodId());
    paymentMethod.setCode(event.getCode());
    paymentMethod.setName(event.getName());
    paymentMethod.setDescription(event.getDescription());
    paymentMethod.setType(PaymentMethodType.valueOf(event.getType()));
    paymentMethod.setProvider(event.getProvider());
    paymentMethod.setCurrency(PaymentCurrency.valueOf(event.getCurrency()));
    paymentMethod.setIconUrl(event.getIconUrl());
    paymentMethod.setIsActive(event.getIsActive());

    // Save the payment method to the repository
    paymentMethodRepository.save(paymentMethod);
  }

  public void updatePaymentMethod(PaymentMethodUpdatedEvent event) {
    // Find the existing payment method by ID
    PaymentMethod paymentMethod = paymentMethodRepository.findById(event.getPaymentMethodId())
            .orElseThrow(() -> new RuntimeException("Payment method not found"));

    // Update the properties of the payment method
    paymentMethod.setCode(event.getCode());
    paymentMethod.setName(event.getName());
    paymentMethod.setDescription(event.getDescription());
    paymentMethod.setType(PaymentMethodType.valueOf(event.getType()));
    paymentMethod.setProvider(event.getProvider());
    paymentMethod.setCurrency(PaymentCurrency.valueOf(event.getCurrency()));
    paymentMethod.setIconUrl(event.getIconUrl());
    paymentMethod.setIsActive(event.getIsActive());

    // Save the updated payment method to the repository
    paymentMethodRepository.save(paymentMethod);
  }

  public void deletePaymentMethod(PaymentMethodDeletedEvent event) {
    // Find the existing payment method by ID
    PaymentMethod paymentMethod = paymentMethodRepository.findById(event.getPaymentMethodId())
            .orElseThrow(() -> new RuntimeException("Payment method not found"));

    // Set the payment method as inactive
    paymentMethod.setIsActive(false);

    // Save the updated payment method to the repository
    paymentMethodRepository.save(paymentMethod);
  }

  public ResponseData findAllPaymentMethods() {
    /// Retrieve all payment methods from the repository.
    List<PaymentMethod> methods = paymentMethodRepository.findAll();
    /// Create a response data object with the list of payment methods.

    List<PaymentMethodDTO> methodDTOs = methods.stream().map(method -> {
      PaymentMethodDTO dto = new PaymentMethodDTO();
      PaymentMethodMapper.mapToPaymentMethodDTO(method, dto);
      return dto;
    }).collect(Collectors.toList());

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Retrieved all payment methods successfully")
            .data(methodDTOs)
            .build();
  }

  public ResponseData findPaymentMethodsByType(FindPaymentMethodsByTypeQuery query) {
    PaymentMethodType type = PaymentMethodType.valueOf(query.getType());
    /// Retrieve payment methods by type from the repository.
    List<PaymentMethod> methods = paymentMethodRepository.findAllByType(type);

    List<PaymentMethodDTO> methodDTOs = methods.stream().map(method -> {
      PaymentMethodDTO dto = new PaymentMethodDTO();
      PaymentMethodMapper.mapToPaymentMethodDTO(method, dto);
      return dto;
    }).collect(Collectors.toList());

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Retrieved all payment methods successfully")
            .data(methodDTOs)
            .build();
  }
}