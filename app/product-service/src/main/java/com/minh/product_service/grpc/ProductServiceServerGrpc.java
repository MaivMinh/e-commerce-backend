package com.minh.product_service.grpc;

import com.minh.product_service.service.ProductService;
import com.minh.product_service_grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;


@Service
@GrpcService
@RequiredArgsConstructor
public class ProductServiceServerGrpc extends ProductServiceGrpc.ProductServiceImplBase {
  private final ProductService productService;

  @Override
  public void findProductVariantById(FindProductVariantByIdRequest request, StreamObserver<FindProductVariantByIdResponse> responseObserver) {
    FindProductVariantByIdResponse response = productService.findProductVariantById(request);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void findProductVariantsByIds(FindProductVariantsByIdsRequest request, StreamObserver<FindProductVariantsByIdsResponse> responseObserver) {
    FindProductVariantsByIdsResponse response = productService.findProductVariantsByIds(request);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}