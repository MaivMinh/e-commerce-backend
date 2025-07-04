// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: product.proto

package com.minh.grpc_service.product;

/**
 * Protobuf type {@code net.devh.boot.grpc.example.FindProductVariantsByIdsResponse}
 */
public final class FindProductVariantsByIdsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:net.devh.boot.grpc.example.FindProductVariantsByIdsResponse)
    FindProductVariantsByIdsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FindProductVariantsByIdsResponse.newBuilder() to construct.
  private FindProductVariantsByIdsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FindProductVariantsByIdsResponse() {
    message_ = "";
    productVariants_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new FindProductVariantsByIdsResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.minh.grpc_service.product.ProductProto.internal_static_net_devh_boot_grpc_example_FindProductVariantsByIdsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.minh.grpc_service.product.ProductProto.internal_static_net_devh_boot_grpc_example_FindProductVariantsByIdsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.minh.grpc_service.product.FindProductVariantsByIdsResponse.class, com.minh.grpc_service.product.FindProductVariantsByIdsResponse.Builder.class);
  }

  public static final int STATUS_FIELD_NUMBER = 1;
  private int status_;
  /**
   * <code>int32 status = 1;</code>
   * @return The status.
   */
  @java.lang.Override
  public int getStatus() {
    return status_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 2;
  private volatile java.lang.Object message_;
  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  @java.lang.Override
  public java.lang.String getMessage() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      message_ = s;
      return s;
    }
  }
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMessageBytes() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      message_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PRODUCTVARIANTS_FIELD_NUMBER = 3;
  private java.util.List<com.minh.grpc_service.product.ProductVariantMessage> productVariants_;
  /**
   * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
   */
  @java.lang.Override
  public java.util.List<com.minh.grpc_service.product.ProductVariantMessage> getProductVariantsList() {
    return productVariants_;
  }
  /**
   * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.minh.grpc_service.product.ProductVariantMessageOrBuilder> 
      getProductVariantsOrBuilderList() {
    return productVariants_;
  }
  /**
   * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
   */
  @java.lang.Override
  public int getProductVariantsCount() {
    return productVariants_.size();
  }
  /**
   * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
   */
  @java.lang.Override
  public com.minh.grpc_service.product.ProductVariantMessage getProductVariants(int index) {
    return productVariants_.get(index);
  }
  /**
   * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
   */
  @java.lang.Override
  public com.minh.grpc_service.product.ProductVariantMessageOrBuilder getProductVariantsOrBuilder(
      int index) {
    return productVariants_.get(index);
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
    if (status_ != 0) {
      output.writeInt32(1, status_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
    }
    for (int i = 0; i < productVariants_.size(); i++) {
      output.writeMessage(3, productVariants_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, status_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
    }
    for (int i = 0; i < productVariants_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, productVariants_.get(i));
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
    if (!(obj instanceof com.minh.grpc_service.product.FindProductVariantsByIdsResponse)) {
      return super.equals(obj);
    }
    com.minh.grpc_service.product.FindProductVariantsByIdsResponse other = (com.minh.grpc_service.product.FindProductVariantsByIdsResponse) obj;

    if (getStatus()
        != other.getStatus()) return false;
    if (!getMessage()
        .equals(other.getMessage())) return false;
    if (!getProductVariantsList()
        .equals(other.getProductVariantsList())) return false;
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
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + getStatus();
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    if (getProductVariantsCount() > 0) {
      hash = (37 * hash) + PRODUCTVARIANTS_FIELD_NUMBER;
      hash = (53 * hash) + getProductVariantsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse parseFrom(
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
  public static Builder newBuilder(com.minh.grpc_service.product.FindProductVariantsByIdsResponse prototype) {
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
   * Protobuf type {@code net.devh.boot.grpc.example.FindProductVariantsByIdsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:net.devh.boot.grpc.example.FindProductVariantsByIdsResponse)
      com.minh.grpc_service.product.FindProductVariantsByIdsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.minh.grpc_service.product.ProductProto.internal_static_net_devh_boot_grpc_example_FindProductVariantsByIdsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.minh.grpc_service.product.ProductProto.internal_static_net_devh_boot_grpc_example_FindProductVariantsByIdsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.minh.grpc_service.product.FindProductVariantsByIdsResponse.class, com.minh.grpc_service.product.FindProductVariantsByIdsResponse.Builder.class);
    }

    // Construct using com.minh.grpc_service.product.FindProductVariantsByIdsResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      status_ = 0;

      message_ = "";

      if (productVariantsBuilder_ == null) {
        productVariants_ = java.util.Collections.emptyList();
      } else {
        productVariants_ = null;
        productVariantsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.minh.grpc_service.product.ProductProto.internal_static_net_devh_boot_grpc_example_FindProductVariantsByIdsResponse_descriptor;
    }

    @java.lang.Override
    public com.minh.grpc_service.product.FindProductVariantsByIdsResponse getDefaultInstanceForType() {
      return com.minh.grpc_service.product.FindProductVariantsByIdsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.minh.grpc_service.product.FindProductVariantsByIdsResponse build() {
      com.minh.grpc_service.product.FindProductVariantsByIdsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.minh.grpc_service.product.FindProductVariantsByIdsResponse buildPartial() {
      com.minh.grpc_service.product.FindProductVariantsByIdsResponse result = new com.minh.grpc_service.product.FindProductVariantsByIdsResponse(this);
      int from_bitField0_ = bitField0_;
      result.status_ = status_;
      result.message_ = message_;
      if (productVariantsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          productVariants_ = java.util.Collections.unmodifiableList(productVariants_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.productVariants_ = productVariants_;
      } else {
        result.productVariants_ = productVariantsBuilder_.build();
      }
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
      if (other instanceof com.minh.grpc_service.product.FindProductVariantsByIdsResponse) {
        return mergeFrom((com.minh.grpc_service.product.FindProductVariantsByIdsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.minh.grpc_service.product.FindProductVariantsByIdsResponse other) {
      if (other == com.minh.grpc_service.product.FindProductVariantsByIdsResponse.getDefaultInstance()) return this;
      if (other.getStatus() != 0) {
        setStatus(other.getStatus());
      }
      if (!other.getMessage().isEmpty()) {
        message_ = other.message_;
        onChanged();
      }
      if (productVariantsBuilder_ == null) {
        if (!other.productVariants_.isEmpty()) {
          if (productVariants_.isEmpty()) {
            productVariants_ = other.productVariants_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureProductVariantsIsMutable();
            productVariants_.addAll(other.productVariants_);
          }
          onChanged();
        }
      } else {
        if (!other.productVariants_.isEmpty()) {
          if (productVariantsBuilder_.isEmpty()) {
            productVariantsBuilder_.dispose();
            productVariantsBuilder_ = null;
            productVariants_ = other.productVariants_;
            bitField0_ = (bitField0_ & ~0x00000001);
            productVariantsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getProductVariantsFieldBuilder() : null;
          } else {
            productVariantsBuilder_.addAllMessages(other.productVariants_);
          }
        }
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
            case 8: {
              status_ = input.readInt32();

              break;
            } // case 8
            case 18: {
              message_ = input.readStringRequireUtf8();

              break;
            } // case 18
            case 26: {
              com.minh.grpc_service.product.ProductVariantMessage m =
                  input.readMessage(
                      com.minh.grpc_service.product.ProductVariantMessage.parser(),
                      extensionRegistry);
              if (productVariantsBuilder_ == null) {
                ensureProductVariantsIsMutable();
                productVariants_.add(m);
              } else {
                productVariantsBuilder_.addMessage(m);
              }
              break;
            } // case 26
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
    private int bitField0_;

    private int status_ ;
    /**
     * <code>int32 status = 1;</code>
     * @return The status.
     */
    @java.lang.Override
    public int getStatus() {
      return status_;
    }
    /**
     * <code>int32 status = 1;</code>
     * @param value The status to set.
     * @return This builder for chaining.
     */
    public Builder setStatus(int value) {
      
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 status = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearStatus() {
      
      status_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object message_ = "";
    /**
     * <code>string message = 2;</code>
     * @return The message.
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string message = 2;</code>
     * @return The bytes for message.
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string message = 2;</code>
     * @param value The message to set.
     * @return This builder for chaining.
     */
    public Builder setMessage(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      message_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMessage() {
      
      message_ = getDefaultInstance().getMessage();
      onChanged();
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @param value The bytes for message to set.
     * @return This builder for chaining.
     */
    public Builder setMessageBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      message_ = value;
      onChanged();
      return this;
    }

    private java.util.List<com.minh.grpc_service.product.ProductVariantMessage> productVariants_ =
      java.util.Collections.emptyList();
    private void ensureProductVariantsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        productVariants_ = new java.util.ArrayList<com.minh.grpc_service.product.ProductVariantMessage>(productVariants_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.minh.grpc_service.product.ProductVariantMessage, com.minh.grpc_service.product.ProductVariantMessage.Builder, com.minh.grpc_service.product.ProductVariantMessageOrBuilder> productVariantsBuilder_;

    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public java.util.List<com.minh.grpc_service.product.ProductVariantMessage> getProductVariantsList() {
      if (productVariantsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(productVariants_);
      } else {
        return productVariantsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public int getProductVariantsCount() {
      if (productVariantsBuilder_ == null) {
        return productVariants_.size();
      } else {
        return productVariantsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public com.minh.grpc_service.product.ProductVariantMessage getProductVariants(int index) {
      if (productVariantsBuilder_ == null) {
        return productVariants_.get(index);
      } else {
        return productVariantsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder setProductVariants(
        int index, com.minh.grpc_service.product.ProductVariantMessage value) {
      if (productVariantsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProductVariantsIsMutable();
        productVariants_.set(index, value);
        onChanged();
      } else {
        productVariantsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder setProductVariants(
        int index, com.minh.grpc_service.product.ProductVariantMessage.Builder builderForValue) {
      if (productVariantsBuilder_ == null) {
        ensureProductVariantsIsMutable();
        productVariants_.set(index, builderForValue.build());
        onChanged();
      } else {
        productVariantsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder addProductVariants(com.minh.grpc_service.product.ProductVariantMessage value) {
      if (productVariantsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProductVariantsIsMutable();
        productVariants_.add(value);
        onChanged();
      } else {
        productVariantsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder addProductVariants(
        int index, com.minh.grpc_service.product.ProductVariantMessage value) {
      if (productVariantsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureProductVariantsIsMutable();
        productVariants_.add(index, value);
        onChanged();
      } else {
        productVariantsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder addProductVariants(
        com.minh.grpc_service.product.ProductVariantMessage.Builder builderForValue) {
      if (productVariantsBuilder_ == null) {
        ensureProductVariantsIsMutable();
        productVariants_.add(builderForValue.build());
        onChanged();
      } else {
        productVariantsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder addProductVariants(
        int index, com.minh.grpc_service.product.ProductVariantMessage.Builder builderForValue) {
      if (productVariantsBuilder_ == null) {
        ensureProductVariantsIsMutable();
        productVariants_.add(index, builderForValue.build());
        onChanged();
      } else {
        productVariantsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder addAllProductVariants(
        java.lang.Iterable<? extends com.minh.grpc_service.product.ProductVariantMessage> values) {
      if (productVariantsBuilder_ == null) {
        ensureProductVariantsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, productVariants_);
        onChanged();
      } else {
        productVariantsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder clearProductVariants() {
      if (productVariantsBuilder_ == null) {
        productVariants_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        productVariantsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public Builder removeProductVariants(int index) {
      if (productVariantsBuilder_ == null) {
        ensureProductVariantsIsMutable();
        productVariants_.remove(index);
        onChanged();
      } else {
        productVariantsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public com.minh.grpc_service.product.ProductVariantMessage.Builder getProductVariantsBuilder(
        int index) {
      return getProductVariantsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public com.minh.grpc_service.product.ProductVariantMessageOrBuilder getProductVariantsOrBuilder(
        int index) {
      if (productVariantsBuilder_ == null) {
        return productVariants_.get(index);  } else {
        return productVariantsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public java.util.List<? extends com.minh.grpc_service.product.ProductVariantMessageOrBuilder> 
         getProductVariantsOrBuilderList() {
      if (productVariantsBuilder_ != null) {
        return productVariantsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(productVariants_);
      }
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public com.minh.grpc_service.product.ProductVariantMessage.Builder addProductVariantsBuilder() {
      return getProductVariantsFieldBuilder().addBuilder(
          com.minh.grpc_service.product.ProductVariantMessage.getDefaultInstance());
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public com.minh.grpc_service.product.ProductVariantMessage.Builder addProductVariantsBuilder(
        int index) {
      return getProductVariantsFieldBuilder().addBuilder(
          index, com.minh.grpc_service.product.ProductVariantMessage.getDefaultInstance());
    }
    /**
     * <code>repeated .net.devh.boot.grpc.example.ProductVariantMessage productVariants = 3;</code>
     */
    public java.util.List<com.minh.grpc_service.product.ProductVariantMessage.Builder> 
         getProductVariantsBuilderList() {
      return getProductVariantsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.minh.grpc_service.product.ProductVariantMessage, com.minh.grpc_service.product.ProductVariantMessage.Builder, com.minh.grpc_service.product.ProductVariantMessageOrBuilder> 
        getProductVariantsFieldBuilder() {
      if (productVariantsBuilder_ == null) {
        productVariantsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.minh.grpc_service.product.ProductVariantMessage, com.minh.grpc_service.product.ProductVariantMessage.Builder, com.minh.grpc_service.product.ProductVariantMessageOrBuilder>(
                productVariants_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        productVariants_ = null;
      }
      return productVariantsBuilder_;
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


    // @@protoc_insertion_point(builder_scope:net.devh.boot.grpc.example.FindProductVariantsByIdsResponse)
  }

  // @@protoc_insertion_point(class_scope:net.devh.boot.grpc.example.FindProductVariantsByIdsResponse)
  private static final com.minh.grpc_service.product.FindProductVariantsByIdsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.minh.grpc_service.product.FindProductVariantsByIdsResponse();
  }

  public static com.minh.grpc_service.product.FindProductVariantsByIdsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FindProductVariantsByIdsResponse>
      PARSER = new com.google.protobuf.AbstractParser<FindProductVariantsByIdsResponse>() {
    @java.lang.Override
    public FindProductVariantsByIdsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<FindProductVariantsByIdsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<FindProductVariantsByIdsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.minh.grpc_service.product.FindProductVariantsByIdsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

