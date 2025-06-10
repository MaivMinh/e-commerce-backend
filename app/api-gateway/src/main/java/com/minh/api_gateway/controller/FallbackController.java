package com.minh.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class FallbackController {
  @RequestMapping(value = "/products/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> productServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Product Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/carts/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> cartServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Cart Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/auth/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> authServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Auth Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/orders/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> orderServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Order Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/payments/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> paymentServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Payment Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/promotions/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> promotionServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Promotion Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/files/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> fileServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "File Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }

  @RequestMapping(value = "/users/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> userServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "User Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }
  @RequestMapping(value = "/reviews/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> reviewServiceContactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Review Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }
}