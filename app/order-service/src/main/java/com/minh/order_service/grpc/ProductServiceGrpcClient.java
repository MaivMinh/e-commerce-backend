package com.minh.order_service.grpc;

import com.minh.grpc_service.product.FindProductVariantByIdRequest;
import com.minh.grpc_service.product.FindProductVariantByIdResponse;
import com.minh.grpc_service.product.ProductServiceGrpc;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceGrpcClient {
  @GrpcClient("product-service")
  @Autowired
  private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
  @Autowired
  private TimeLimiterRegistry timeLimiterRegistry;



  public FindProductVariantByIdResponse findProductVariantById(FindProductVariantByIdRequest request) throws Exception {
    TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter("productService");
    return timeLimiter.executeFutureSupplier(
            () -> CompletableFuture.supplyAsync(() -> productServiceBlockingStub.findProductVariantById(request)));
  }
}
