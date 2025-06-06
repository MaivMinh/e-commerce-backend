package com.minh.payment_service.service;

import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.command.events.PaymentMethodCreatedEvent;
import com.minh.payment_service.command.events.PaymentMethodDeletedEvent;
import com.minh.payment_service.command.events.PaymentMethodUpdatedEvent;
import com.minh.payment_service.entity.PaymentCurrency;
import com.minh.payment_service.entity.PaymentMethod;
import com.minh.payment_service.entity.PaymentMethodType;
import com.minh.payment_service.mapper.PaymentMethodMapper;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsQuery;
import com.minh.payment_service.query.queries.FindAllPaymentMethodsWithoutParamsQuery;
import com.minh.payment_service.query.queries.FindPaymentMethodsByTypeQuery;
import com.minh.payment_service.repository.PaymentMethodRepository;
import com.minh.payment_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minh.payment_service.specification.PaymentMethodSpecification.*;

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
    paymentMethodRepository.delete(paymentMethod);
  }

  public ResponseData findAllPaymentMethods(FindAllPaymentMethodsQuery query) {
    /// Retrieve all payment methods from the repository.
    String search = query.getSearch();
    String filter = query.getFilter();
    Boolean isActive = query.getIsActive();
    int size = query.getSize();
    int page = query.getPage();
    Pageable pageable = PageRequest.of(page, size);
    Specification<PaymentMethod> specification = Specification.where(null);
    if (StringUtils.hasText(search)) {
      specification = specification.and(containsName(search));
    }
    if (StringUtils.hasText(filter)) {
      specification = specification.and(paymentMethodType(PaymentMethodType.valueOf(filter)));
    }
    if (isActive != null) {
      specification = specification.and(isActive(isActive));
    }
    Page<PaymentMethod> methods = paymentMethodRepository.findAll(specification, pageable);
    /// Create a response data object with the list of payment methods.
    List<PaymentMethodDTO> methodDTOs = methods.stream().map(method -> {
      PaymentMethodDTO dto = new PaymentMethodDTO();
      PaymentMethodMapper.mapToPaymentMethodDTO(method, dto);
      return dto;
    }).collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("methods", methodDTOs);
    data.put("totalElements", methods.getTotalElements());
    data.put("totalPages", methods.getTotalPages());
    data.put("page", methods.getNumber() + 1); // Convert to 1-based index
    data.put("size", methods.getSize());

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Retrieved all payment methods successfully")
            .data(data)
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

  public ResponseData findAllPaymentMethodsWithoutParams(FindAllPaymentMethodsWithoutParamsQuery query) {
    /// Retrieve all payment methods from the repository without any parameters.
    List<PaymentMethod> methods = paymentMethodRepository.findAll();

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