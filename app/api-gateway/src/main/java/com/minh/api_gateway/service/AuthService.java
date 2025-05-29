package com.minh.api_gateway.service;

import com.minh.api_gateway.grpc.AuthServiceGrpcClient;
import com.minh.grpc_service.auth.AuthRequest;
import com.minh.grpc_service.auth.AuthResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthServiceGrpcClient authServiceGrpcClient;

  public AuthResponse authenticate(String token) {
    try {
      AuthRequest request = AuthRequest.newBuilder().setToken(token).build();
      return (AuthResponse) Mono.create(sink -> {
        try {
          authServiceGrpcClient.authenticate(request, new StreamObserver<>() {
            @Override
            public void onNext(AuthResponse value) {
              sink.success(value);
            }

            @Override
            public void onError(Throwable t) {
              sink.error(t);
            }

            @Override
            public void onCompleted() {
              sink.success();
            }
          });
        } catch (Exception e) {
          sink.error(new RuntimeException(e));
        }
      }).toFuture().get();

    } catch (Exception e) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setMessage("Error occurred while calling auth service: " + e.getMessage())
              .build();
    }
  }
}