package com.vollify.smart.device.exception;

public abstract class DeviceServiceException extends Exception {

  public DeviceServiceException(String message) {
    super(message);
  }

  public DeviceServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
