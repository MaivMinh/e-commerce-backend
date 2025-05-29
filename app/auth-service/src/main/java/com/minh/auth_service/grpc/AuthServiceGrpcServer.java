package com.minh.auth_service.grpc;


import com.minh.auth_service.services.AuthService;
import com.minh.grpc_service.auth.AuthRequest;
import com.minh.grpc_service.auth.AuthResponse;
import com.minh.grpc_service.auth.AuthServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class AuthServiceGrpcServer extends AuthServiceGrpc.AuthServiceImplBase {
  private final AuthService authService;

  @Override
  public void authenticate(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {
    System.out.println("Received authentication request: " + request.getToken());
    AuthResponse response = authService.authenticate(request);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}