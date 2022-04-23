package com.vollify.smart.device.exception;

public class EntityNotFoundException extends DeviceServiceException {
    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
