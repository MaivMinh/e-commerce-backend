package com.minh.order_service.config;

import com.minh.grpc_service.product.ProductServiceGrpc;
import com.minh.grpc_service.user.UserServiceGrpc;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
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

    String name = env.getProperty("GRPC_SERVER_PRODUCT_SERVICE_NAME", "localhost");
    int port = Integer.parseInt(env.getProperty("GRPC_SERVER_PRODUCT_SERVICE_PORT", "9091"));

    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here
    return ProductServiceGrpc.newBlockingStub(io.grpc.ManagedChannelBuilder.forAddress(name, port).usePlaintext().build());
  }

  @Bean
  public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
    // This bean is typically auto-configured by Spring Boot with gRPC
    // If you need to customize it, you can do so here

    String name = env.getProperty("GRPC_SERVER_USER_SERVICE_NAME", "localhost");
    int port = Integer.parseInt(env.getProperty("GRPC_SERVER_USER_SERVICE_PORT", "9098"));


    return UserServiceGrpc.newBlockingStub(io.grpc.ManagedChannelBuilder.forAddress(name, port).usePlaintext().build());
  }
}
