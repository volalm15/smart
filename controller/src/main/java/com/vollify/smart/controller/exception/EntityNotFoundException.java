package com.vollify.smart.controller.exception;

public class EntityNotFoundException extends ControllerServiceException {
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
