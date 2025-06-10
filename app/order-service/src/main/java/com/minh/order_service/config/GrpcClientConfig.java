package com.minh.order_service.config;

import com.minh.grpc_service.product.ProductServiceGrpc;
import com.minh.grpc_service.user.UserServiceGrpc;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class GrpcClientConfig {
  private final Environment env;

  @Bean
  public TimeLimiterRegistry timeLimiterRegistry() {
    // Create a TimeLimiterRegistry with default configuration
    return TimeLimiterRegistry.ofDefaults();
  }

  @Bean
  public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub() {

    String address = env.getProperty("GRPC_SERVER_PRODUCT_SERVICE_ADDRESS", "static://localhost:9091");

    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here
    return ProductServiceGrpc.newBlockingStub(ManagedChannelBuilder.forTarget(address).usePlaintext().build());
  }

  @Bean
  public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here

    String address = env.getProperty("GRPC_SERVER_USER_SERVICE_ADDRESS", "static://localhost:9098");
    return UserServiceGrpc.newBlockingStub(io.grpc.ManagedChannelBuilder.forTarget(address).usePlaintext().build());
  }
}
