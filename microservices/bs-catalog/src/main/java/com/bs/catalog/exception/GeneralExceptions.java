package com.bs.catalog.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.HashMap;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GeneralExceptions {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
    var errors = new HashMap<>();
    var response = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    response.put("timestamp", LocalDateTime.now());
    response.put("status", BAD_REQUEST.value());
    response.put("errors", errors);

    return ResponseEntity.badRequest().body(response);
  }
}
