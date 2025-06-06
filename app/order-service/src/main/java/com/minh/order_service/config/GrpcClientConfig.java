package com.minh.order_service.config;

import com.minh.grpc_service.product.ProductServiceGrpc;
import com.minh.grpc_service.user.UserServiceGrpc;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
  @Bean
  public TimeLimiterRegistry timeLimiterRegistry() {
    // Create a TimeLimiterRegistry with default configuration
    return TimeLimiterRegistry.ofDefaults();
  }

  @Bean
  public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub() {
    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here
    return ProductServiceGrpc.newBlockingStub(io.grpc.ManagedChannelBuilder.forAddress("localhost", 9091).usePlaintext().build());
  }

  @Bean
  public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here
    return UserServiceGrpc.newBlockingStub(io.grpc.ManagedChannelBuilder.forAddress("localhost", 9098).usePlaintext().build());
  }
}
