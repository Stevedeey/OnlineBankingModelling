package com.banking.onlinebankingwebapi.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private String message;

    private boolean success;

    private HttpStatus httpStatus;

    private ZonedDateTime timestamp;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, boolean success) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }
}
