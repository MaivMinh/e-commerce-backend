package com.minh.user_service.grpc;

import com.minh.grpc_service.user.GetUserInfoRequest;
import com.minh.grpc_service.user.GetUserInfoResponse;
import com.minh.grpc_service.user.UserServiceGrpc;
import com.minh.user_service.service.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcServer extends UserServiceGrpc.UserServiceImplBase {
  private final UserService userService;

  @Override
  public void getUserInfo(GetUserInfoRequest request, StreamObserver<GetUserInfoResponse> responseObserver) {
    GetUserInfoResponse response = userService.getUserInfo(request);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}