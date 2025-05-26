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
@RequestMapping("/products")
public class ProductServiceFallbackController {
  @RequestMapping(value = "/contact-support")
  public Mono<ResponseEntity<Map<String, Object>>> contactSupport() {
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("message", "Product Service is currently unavailable. Please contact support team!");
    response.put("timestamp", new Date());

    return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(response));
  }
}