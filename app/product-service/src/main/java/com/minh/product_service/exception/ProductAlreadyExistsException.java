package com.minh.product_service.exception;

public class ProductAlreadyExistsException extends Throwable {
  public ProductAlreadyExistsException(String message) {
    super(message);
  }

  public ProductAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
