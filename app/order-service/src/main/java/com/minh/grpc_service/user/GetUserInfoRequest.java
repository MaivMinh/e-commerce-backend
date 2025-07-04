// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package com.minh.grpc_service.user;

/**
 * Protobuf type {@code net.devh.boot.grpc.example.GetUserInfoRequest}
 */
public final class GetUserInfoRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:net.devh.boot.grpc.example.GetUserInfoRequest)
    GetUserInfoRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetUserInfoRequest.newBuilder() to construct.
  private GetUserInfoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetUserInfoRequest() {
    accountId_ = "";
    shippingAddressId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetUserInfoRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.minh.grpc_service.user.UserProto.internal_static_net_devh_boot_grpc_example_GetUserInfoRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.minh.grpc_service.user.UserProto.internal_static_net_devh_boot_grpc_example_GetUserInfoRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.minh.grpc_service.user.GetUserInfoRequest.class, com.minh.grpc_service.user.GetUserInfoRequest.Builder.class);
  }

  public static final int ACCOUNTID_FIELD_NUMBER = 1;
  private volatile java.lang.Object accountId_;
  /**
   * <code>string accountId = 1;</code>
   * @return The accountId.
   */
  @java.lang.Override
  public java.lang.String getAccountId() {
    java.lang.Object ref = accountId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      accountId_ = s;
      return s;
    }
  }
  /**
   * <code>string accountId = 1;</code>
   * @return The bytes for accountId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAccountIdBytes() {
    java.lang.Object ref = accountId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      accountId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SHIPPINGADDRESSID_FIELD_NUMBER = 2;
  private volatile java.lang.Object shippingAddressId_;
  /**
   * <code>string shippingAddressId = 2;</code>
   * @return The shippingAddressId.
   */
  @java.lang.Override
  public java.lang.String getShippingAddressId() {
    java.lang.Object ref = shippingAddressId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      shippingAddressId_ = s;
      return s;
    }
  }
  /**
   * <code>string shippingAddressId = 2;</code>
   * @return The bytes for shippingAddressId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getShippingAddressIdBytes() {
    java.lang.Object ref = shippingAddressId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      shippingAddressId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(accountId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, accountId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(shippingAddressId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, shippingAddressId_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(accountId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, accountId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(shippingAddressId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, shippingAddressId_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.minh.grpc_service.user.GetUserInfoRequest)) {
      return super.equals(obj);
    }
    com.minh.grpc_service.user.GetUserInfoRequest other = (com.minh.grpc_service.user.GetUserInfoRequest) obj;

    if (!getAccountId()
        .equals(other.getAccountId())) return false;
    if (!getShippingAddressId()
        .equals(other.getShippingAddressId())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ACCOUNTID_FIELD_NUMBER;
    hash = (53 * hash) + getAccountId().hashCode();
    hash = (37 * hash) + SHIPPINGADDRESSID_FIELD_NUMBER;
    hash = (53 * hash) + getShippingAddressId().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.user.GetUserInfoRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.minh.grpc_service.user.GetUserInfoRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code net.devh.boot.grpc.example.GetUserInfoRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:net.devh.boot.grpc.example.GetUserInfoRequest)
      com.minh.grpc_service.user.GetUserInfoRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.minh.grpc_service.user.UserProto.internal_static_net_devh_boot_grpc_example_GetUserInfoRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.minh.grpc_service.user.UserProto.internal_static_net_devh_boot_grpc_example_GetUserInfoRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.minh.grpc_service.user.GetUserInfoRequest.class, com.minh.grpc_service.user.GetUserInfoRequest.Builder.class);
    }

    // Construct using com.minh.grpc_service.user.GetUserInfoRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      accountId_ = "";

      shippingAddressId_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.minh.grpc_service.user.UserProto.internal_static_net_devh_boot_grpc_example_GetUserInfoRequest_descriptor;
    }

    @java.lang.Override
    public com.minh.grpc_service.user.GetUserInfoRequest getDefaultInstanceForType() {
      return com.minh.grpc_service.user.GetUserInfoRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.minh.grpc_service.user.GetUserInfoRequest build() {
      com.minh.grpc_service.user.GetUserInfoRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.minh.grpc_service.user.GetUserInfoRequest buildPartial() {
      com.minh.grpc_service.user.GetUserInfoRequest result = new com.minh.grpc_service.user.GetUserInfoRequest(this);
      result.accountId_ = accountId_;
      result.shippingAddressId_ = shippingAddressId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.minh.grpc_service.user.GetUserInfoRequest) {
        return mergeFrom((com.minh.grpc_service.user.GetUserInfoRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.minh.grpc_service.user.GetUserInfoRequest other) {
      if (other == com.minh.grpc_service.user.GetUserInfoRequest.getDefaultInstance()) return this;
      if (!other.getAccountId().isEmpty()) {
        accountId_ = other.accountId_;
        onChanged();
      }
      if (!other.getShippingAddressId().isEmpty()) {
        shippingAddressId_ = other.shippingAddressId_;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              accountId_ = input.readStringRequireUtf8();

              break;
            } // case 10
            case 18: {
              shippingAddressId_ = input.readStringRequireUtf8();

              break;
            } // case 18
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }

    private java.lang.Object accountId_ = "";
    /**
     * <code>string accountId = 1;</code>
     * @return The accountId.
     */
    public java.lang.String getAccountId() {
      java.lang.Object ref = accountId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        accountId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string accountId = 1;</code>
     * @return The bytes for accountId.
     */
    public com.google.protobuf.ByteString
        getAccountIdBytes() {
      java.lang.Object ref = accountId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        accountId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string accountId = 1;</code>
     * @param value The accountId to set.
     * @return This builder for chaining.
     */
    public Builder setAccountId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      accountId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string accountId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearAccountId() {
      
      accountId_ = getDefaultInstance().getAccountId();
      onChanged();
      return this;
    }
    /**
     * <code>string accountId = 1;</code>
     * @param value The bytes for accountId to set.
     * @return This builder for chaining.
     */
    public Builder setAccountIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      accountId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object shippingAddressId_ = "";
    /**
     * <code>string shippingAddressId = 2;</code>
     * @return The shippingAddressId.
     */
    public java.lang.String getShippingAddressId() {
      java.lang.Object ref = shippingAddressId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        shippingAddressId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string shippingAddressId = 2;</code>
     * @return The bytes for shippingAddressId.
     */
    public com.google.protobuf.ByteString
        getShippingAddressIdBytes() {
      java.lang.Object ref = shippingAddressId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        shippingAddressId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string shippingAddressId = 2;</code>
     * @param value The shippingAddressId to set.
     * @return This builder for chaining.
     */
    public Builder setShippingAddressId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      shippingAddressId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string shippingAddressId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearShippingAddressId() {
      
      shippingAddressId_ = getDefaultInstance().getShippingAddressId();
      onChanged();
      return this;
    }
    /**
     * <code>string shippingAddressId = 2;</code>
     * @param value The bytes for shippingAddressId to set.
     * @return This builder for chaining.
     */
    public Builder setShippingAddressIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      shippingAddressId_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:net.devh.boot.grpc.example.GetUserInfoRequest)
  }

  // @@protoc_insertion_point(class_scope:net.devh.boot.grpc.example.GetUserInfoRequest)
  private static final com.minh.grpc_service.user.GetUserInfoRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.minh.grpc_service.user.GetUserInfoRequest();
  }

  public static com.minh.grpc_service.user.GetUserInfoRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetUserInfoRequest>
      PARSER = new com.google.protobuf.AbstractParser<GetUserInfoRequest>() {
    @java.lang.Override
    public GetUserInfoRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<GetUserInfoRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetUserInfoRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.minh.grpc_service.user.GetUserInfoRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

