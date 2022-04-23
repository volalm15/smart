package com.vollify.smart.device.exception;

import com.mongodb.MongoServerException;
import com.vollify.smart.device.exception.ApiError;
import com.vollify.smart.device.exception.ApiSubError;
import com.vollify.smart.device.exception.ApiValidationError;
import com.vollify.smart.device.exception.DeviceServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String errorString = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errorString, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String errorString = "Request was malformed. Please see 'causes' for further information.";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorString, ex);

        final List<ApiSubError> apiSubErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            apiSubErrors.add(new ApiValidationError(fieldName, ((FieldError) error).getRejectedValue(), errorMessage));
        });

        apiError.setCauses(apiSubErrors);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleIllegalArgument(ValidationException ex) {
        var errorString = "Internal Validation Exception.";
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, errorString, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({DeviceServiceException.class, MongoServerException.class})
    protected ResponseEntity<Object> handleIllegalArgument(Exception ex) {
        final String errorString = "General device service exception. Please see 'causes' for further information.";
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorString, ex);
        return buildResponseEntity(apiError);
    }

}