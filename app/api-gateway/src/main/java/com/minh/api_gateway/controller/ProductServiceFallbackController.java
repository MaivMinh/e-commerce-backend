package com.minh.api_gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductServiceFallbackController {
  @RequestMapping(value = "/contact-support")
  public Mono<String> contactSupport() {
    return Mono.just("Product Service is currently unavailable. Please contact support team!");
  }
}