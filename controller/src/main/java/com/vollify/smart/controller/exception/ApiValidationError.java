package com.vollify.smart.controller.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ApiValidationError extends ApiSubError {

    String field;

    Object rejectedValue;

    String message;

}