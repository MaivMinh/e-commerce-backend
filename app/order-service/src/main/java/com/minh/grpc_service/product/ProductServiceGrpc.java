package com.minh.grpc_service.product;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.69.0)",
    comments = "Source: product.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductServiceGrpc {

  private ProductServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "net.devh.boot.grpc.example.ProductService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantByIdRequest,
      com.minh.grpc_service.product.FindProductVariantByIdResponse> getFindProductVariantByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindProductVariantById",
      requestType = com.minh.grpc_service.product.FindProductVariantByIdRequest.class,
      responseType = com.minh.grpc_service.product.FindProductVariantByIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantByIdRequest,
      com.minh.grpc_service.product.FindProductVariantByIdResponse> getFindProductVariantByIdMethod() {
    io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantByIdRequest, com.minh.grpc_service.product.FindProductVariantByIdResponse> getFindProductVariantByIdMethod;
    if ((getFindProductVariantByIdMethod = ProductServiceGrpc.getFindProductVariantByIdMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getFindProductVariantByIdMethod = ProductServiceGrpc.getFindProductVariantByIdMethod) == null) {
          ProductServiceGrpc.getFindProductVariantByIdMethod = getFindProductVariantByIdMethod =
              io.grpc.MethodDescriptor.<com.minh.grpc_service.product.FindProductVariantByIdRequest, com.minh.grpc_service.product.FindProductVariantByIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindProductVariantById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.minh.grpc_service.product.FindProductVariantByIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.minh.grpc_service.product.FindProductVariantByIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("FindProductVariantById"))
              .build();
        }
      }
    }
    return getFindProductVariantByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantsByIdsRequest,
      com.minh.grpc_service.product.FindProductVariantsByIdsResponse> getFindProductVariantsByIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindProductVariantsByIds",
      requestType = com.minh.grpc_service.product.FindProductVariantsByIdsRequest.class,
      responseType = com.minh.grpc_service.product.FindProductVariantsByIdsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantsByIdsRequest,
      com.minh.grpc_service.product.FindProductVariantsByIdsResponse> getFindProductVariantsByIdsMethod() {
    io.grpc.MethodDescriptor<com.minh.grpc_service.product.FindProductVariantsByIdsRequest, com.minh.grpc_service.product.FindProductVariantsByIdsResponse> getFindProductVariantsByIdsMethod;
    if ((getFindProductVariantsByIdsMethod = ProductServiceGrpc.getFindProductVariantsByIdsMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getFindProductVariantsByIdsMethod = ProductServiceGrpc.getFindProductVariantsByIdsMethod) == null) {
          ProductServiceGrpc.getFindProductVariantsByIdsMethod = getFindProductVariantsByIdsMethod =
              io.grpc.MethodDescriptor.<com.minh.grpc_service.product.FindProductVariantsByIdsRequest, com.minh.grpc_service.product.FindProductVariantsByIdsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindProductVariantsByIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.minh.grpc_service.product.FindProductVariantsByIdsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.minh.grpc_service.product.FindProductVariantsByIdsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("FindProductVariantsByIds"))
              .build();
        }
      }
    }
    return getFindProductVariantsByIdsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub>() {
        @java.lang.Override
        public ProductServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceStub(channel, callOptions);
        }
      };
    return ProductServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub>() {
        @java.lang.Override
        public ProductServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceBlockingStub(channel, callOptions);
        }
      };
    return ProductServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub>() {
        @java.lang.Override
        public ProductServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceFutureStub(channel, callOptions);
        }
      };
    return ProductServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void findProductVariantById(com.minh.grpc_service.product.FindProductVariantByIdRequest request,
        io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantByIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindProductVariantByIdMethod(), responseObserver);
    }

    /**
     */
    default void findProductVariantsByIds(com.minh.grpc_service.product.FindProductVariantsByIdsRequest request,
        io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantsByIdsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindProductVariantsByIdsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ProductService.
   */
  public static abstract class ProductServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ProductServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ProductService.
   */
  public static final class ProductServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ProductServiceStub> {
    private ProductServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceStub(channel, callOptions);
    }

    /**
     */
    public void findProductVariantById(com.minh.grpc_service.product.FindProductVariantByIdRequest request,
        io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantByIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindProductVariantByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findProductVariantsByIds(com.minh.grpc_service.product.FindProductVariantsByIdsRequest request,
        io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantsByIdsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindProductVariantsByIdsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ProductService.
   */
  public static final class ProductServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ProductServiceBlockingStub> {
    private ProductServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.minh.grpc_service.product.FindProductVariantByIdResponse findProductVariantById(com.minh.grpc_service.product.FindProductVariantByIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindProductVariantByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.minh.grpc_service.product.FindProductVariantsByIdsResponse findProductVariantsByIds(com.minh.grpc_service.product.FindProductVariantsByIdsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindProductVariantsByIdsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ProductService.
   */
  public static final class ProductServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ProductServiceFutureStub> {
    private ProductServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.minh.grpc_service.product.FindProductVariantByIdResponse> findProductVariantById(
        com.minh.grpc_service.product.FindProductVariantByIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindProductVariantByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.minh.grpc_service.product.FindProductVariantsByIdsResponse> findProductVariantsByIds(
        com.minh.grpc_service.product.FindProductVariantsByIdsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindProductVariantsByIdsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_PRODUCT_VARIANT_BY_ID = 0;
  private static final int METHODID_FIND_PRODUCT_VARIANTS_BY_IDS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_PRODUCT_VARIANT_BY_ID:
          serviceImpl.findProductVariantById((com.minh.grpc_service.product.FindProductVariantByIdRequest) request,
              (io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantByIdResponse>) responseObserver);
          break;
        case METHODID_FIND_PRODUCT_VARIANTS_BY_IDS:
          serviceImpl.findProductVariantsByIds((com.minh.grpc_service.product.FindProductVariantsByIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.minh.grpc_service.product.FindProductVariantsByIdsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getFindProductVariantByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.minh.grpc_service.product.FindProductVariantByIdRequest,
              com.minh.grpc_service.product.FindProductVariantByIdResponse>(
                service, METHODID_FIND_PRODUCT_VARIANT_BY_ID)))
        .addMethod(
          getFindProductVariantsByIdsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.minh.grpc_service.product.FindProductVariantsByIdsRequest,
              com.minh.grpc_service.product.FindProductVariantsByIdsResponse>(
                service, METHODID_FIND_PRODUCT_VARIANTS_BY_IDS)))
        .build();
  }

  private static abstract class ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.minh.grpc_service.product.ProductProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductService");
    }
  }

  private static final class ProductServiceFileDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier {
    ProductServiceFileDescriptorSupplier() {}
  }

  private static final class ProductServiceMethodDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ProductServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProductServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductServiceFileDescriptorSupplier())
              .addMethod(getFindProductVariantByIdMethod())
              .addMethod(getFindProductVariantsByIdsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
