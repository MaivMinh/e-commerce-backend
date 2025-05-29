package com.minh.auth_service.exception;

import com.minh.auth_service.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest webRequest) {
    Map<String, String> errors = new HashMap<>();
    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
    for (ObjectError error : allErrors) {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    }
    ResponseError errorResponse = new ResponseError(HttpStatus.BAD_REQUEST.value(), errors.toString(), webRequest.getDescription(false), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ResponseError> handleRuntimeException(RuntimeException exception,
                                                             WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            webRequest.getDescription(false),
            exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseError> handleGlobalException(Exception exception,
                                                             WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            webRequest.getDescription(false),
            exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(errorResponse);
  }
}
