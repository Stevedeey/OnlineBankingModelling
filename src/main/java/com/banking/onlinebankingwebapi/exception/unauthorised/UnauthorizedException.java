package com.banking.onlinebankingwebapi.exception.unauthorised;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class UnauthorizedException {
    private final String message;

    private final boolean success;

    private final HttpStatus httpStatus;

    private ZonedDateTime timestamp;

    public UnauthorizedException(String message, boolean success, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.success = success;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
