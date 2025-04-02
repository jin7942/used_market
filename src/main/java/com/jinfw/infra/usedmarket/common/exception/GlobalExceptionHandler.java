package com.jinfw.infra.usedmarket.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // 예외처리 리팩토링
  @ExceptionHandler(InvalidLoginException.class)
  public ResponseEntity<String> handleInvalidLoginException(InvalidLoginException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

}
