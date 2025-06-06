package com.minh.order_service.grpc;


import com.minh.grpc_service.user.GetUserInfoRequest;
import com.minh.grpc_service.user.GetUserInfoResponse;
import com.minh.grpc_service.user.UserServiceGrpc;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceGrpcClient {
  @GrpcClient("user-service")
  @Autowired
  private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
  @Autowired
  private TimeLimiterRegistry timeLimiterRegistry;

  private GetUserInfoResponse fallbackGetUserInfo(
          GetUserInfoRequest request,
          Throwable throwable) {
    String message = throwable instanceof CallNotPermittedException
            ? "Circuit breaker in open state: Too many failures in movie service"
            : "Error occurred while calling product service: " + throwable.getMessage();
    return GetUserInfoResponse.newBuilder()
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .setMessage(message)
            .build();
  }

  @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserInfo")
  public GetUserInfoResponse getUserInfo(GetUserInfoRequest request) throws Exception {
    TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter("userService");
    return timeLimiter.executeFutureSupplier(
            () -> CompletableFuture.supplyAsync(() -> userServiceBlockingStub.getUserInfo(request)));
  }
}