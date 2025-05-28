package com.minh.cart_service.exception;

public class CartAlreadyExistsException extends Throwable {
  public CartAlreadyExistsException(String message) {
    super(message);
  }

  public CartAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
