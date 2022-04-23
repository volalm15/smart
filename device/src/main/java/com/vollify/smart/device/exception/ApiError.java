package com.vollify.smart.device.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final long traceId;

    private HttpStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;

    private String message;

  // @JsonIgnore
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> causes;

    private ApiError() {
        traceId = SECURE_RANDOM.nextLong();
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
