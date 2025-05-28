package com.minh.cart_service.exception;

public class ResourceNotFoundException extends Throwable {
  public ResourceNotFoundException(String message) {
    super(message);
  }
  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
