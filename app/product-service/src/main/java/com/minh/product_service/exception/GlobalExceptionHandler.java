package com.minh.product_service.exception;

import com.minh.product_service.response.ResponseError;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.distributed.CommandDispatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
    ResponseError errorResponse = new ResponseError(HttpStatus.BAD_REQUEST.value(), webRequest.getDescription(false), errors.toString(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
  }

  @ExceptionHandler(CommandExecutionException.class)
  public ResponseEntity<ResponseError> handleGlobalException(CommandExecutionException exception,
                                                             WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            webRequest.getDescription(false),
            "CommandExecutionException occurred due to: " + exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(errorResponse);
  }

  @ExceptionHandler(CommandDispatchException.class)
  public ResponseEntity<ResponseError> handleGlobalException(CommandDispatchException exception,
                                                             WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            webRequest.getDescription(false),
            "CommandDispatchException occurred due to: " + exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(errorResponse);
  }



  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ResponseError> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.NOT_FOUND.value(),
            webRequest.getDescription(false),
            exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(errorResponse);
  }

  @ExceptionHandler(ProductAlreadyExistsException.class)
  public ResponseEntity<ResponseError> handleProductAlreadyExistsException(ProductAlreadyExistsException exception,
                                                                           WebRequest webRequest) {
    ResponseError errorResponse = new ResponseError(
            HttpStatus.BAD_REQUEST.value(),
            webRequest.getDescription(false),
            exception.getMessage(),
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
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
