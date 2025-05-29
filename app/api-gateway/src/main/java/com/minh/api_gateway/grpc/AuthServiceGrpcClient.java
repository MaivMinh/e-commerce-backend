package com.minh.api_gateway.grpc;

import com.minh.grpc_service.auth.AuthRequest;
import com.minh.grpc_service.auth.AuthResponse;
import com.minh.grpc_service.auth.AuthServiceGrpc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceGrpcClient extends AuthServiceGrpc.AuthServiceImplBase {
  @GrpcClient("authService")
  private AuthServiceGrpc.AuthServiceStub authServiceStub;
  private final TimeLimiterRegistry timeLimiterRegistry;

  private void authenticateFallback(AuthRequest request, StreamObserver<AuthResponse> responseObserver, Throwable t) {
    log.error("Fallback method called due to: {}", t.getMessage());
    AuthResponse response = AuthResponse.newBuilder()
            .setIsValid(false)
            .setMessage("Service is currently unavailable. Please try again later.")
            .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @CircuitBreaker(name = "authService", fallbackMethod = "authenticateFallback")
  @Override
  public void authenticate(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {
    authServiceStub.authenticate(request, new StreamObserver<AuthResponse>() {
      @Override
      public void onNext(AuthResponse value) {
        responseObserver.onNext(value);
      }

      @Override
      public void onError(Throwable t) {
        t.printStackTrace();
        log.error("Error occurred while calling auth service: {}", t.getMessage());
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        log.info("Auth service call completed");
        responseObserver.onCompleted();
      }
    });
  }
}