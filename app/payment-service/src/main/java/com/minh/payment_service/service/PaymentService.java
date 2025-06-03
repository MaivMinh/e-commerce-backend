package com.minh.payment_service.service;

import com.minh.common.events.PaymentProcessedEvent;
import com.minh.payment_service.entity.Payment;
import com.minh.payment_service.entity.PaymentCurrency;
import com.minh.payment_service.entity.PaymentStatus;
import com.minh.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepository paymentRepository;

  public void processPayment(PaymentProcessedEvent event) {
    /// Hàm thực hiện xử lý thanh toán
    /// 1. Lưu thông tin thanh toán vào cơ sở dữ liệu

    Payment payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    payment.setOrderId(event.getOrderId());
    payment.setPaymentMethodId(event.getPaymentMethodId());
    payment.setAmount(event.getAmount());
    payment.setCurrency(PaymentCurrency.valueOf(event.getCurrency()));
    payment.setStatus(PaymentStatus.pending);
    payment.setTransactionId(UUID.randomUUID().toString()); /// Do không có xử lý thanh toán thực tế(hoặc Sandbox), nên tạo transactionId ngẫu nhiên.
    payment.setPaymentDate(Timestamp.valueOf(LocalDateTime.now()));
    paymentRepository.save(payment);
  }
}
