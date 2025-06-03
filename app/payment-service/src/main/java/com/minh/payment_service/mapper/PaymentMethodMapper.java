package com.minh.payment_service.mapper;

import com.minh.payment_service.DTOs.PaymentMethodDTO;
import com.minh.payment_service.entity.PaymentMethod;

public class PaymentMethodMapper {
  public static void mapToPaymentMethodDTO(PaymentMethod paymentMethod, PaymentMethodDTO paymentMethodDTO) {
    paymentMethodDTO.setPaymentMethodId(paymentMethod.getId());
    paymentMethodDTO.setCode(paymentMethod.getCode());
    paymentMethodDTO.setName(paymentMethod.getName());
    paymentMethodDTO.setType(paymentMethod.getType().toString());
    paymentMethodDTO.setDescription(paymentMethod.getDescription());
    paymentMethodDTO.setCurrency(paymentMethod.getCurrency().toString());
    paymentMethodDTO.setIconUrl(paymentMethod.getIconUrl());
    paymentMethodDTO.setIsActive(paymentMethod.getIsActive());
    paymentMethodDTO.setProvider(paymentMethod.getProvider());
  }
}
