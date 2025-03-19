package com.jinfw.infra.usedmarket.common.exception;

public class InvalidLoginException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public InvalidLoginException(String message) {
    super(message);
  }
}
