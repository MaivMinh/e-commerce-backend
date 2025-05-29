package com.minh.api_gateway.interceptor;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.utils.CircuitBreakerUtil;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CircuitBreakerClientInterceptor implements ClientInterceptor {
  private final CircuitBreaker circuitBreaker;

  public CircuitBreakerClientInterceptor(CircuitBreaker productService) {
    this.circuitBreaker = productService;
  }

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT>
  interceptCall(MethodDescriptor<ReqT, RespT> method,
                CallOptions callOptions, Channel next) {
    log.info("Checking circuit breaker state for method: {}", method.getFullMethodName());
    return new ClientInterceptors.CheckedForwardingClientCall<ReqT, RespT>(
            next.newCall(method, callOptions)) {
      @Override
      protected void checkedStart(
              Listener<RespT> responseListener,
              Metadata headers) {
        CircuitBreakerUtil.isCallPermitted(circuitBreaker);
        delegate().start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
          private final long startTime = System.nanoTime();

          @Override
          public void onClose(Status status, Metadata trailers) {
            long duration = System.nanoTime() - startTime;
            if (status.isOk()) {
              circuitBreaker.onSuccess(duration, TimeUnit.NANOSECONDS);
            } else {
              circuitBreaker.onError(duration,
                      TimeUnit.NANOSECONDS,
                      status.asRuntimeException(trailers));
            }
            super.onClose(status, trailers);
          }
        }, headers);
      }
    };
  }
}