package com.vollify.smart.controller.exception;

public abstract class ControllerServiceException extends Exception {

  public ControllerServiceException(String message) {
    super(message);
  }

  public ControllerServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
