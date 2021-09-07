package com.banking.onlinebankingwebapi.exception;

public class ApiResourceNotFoundException extends RuntimeException{
    public ApiResourceNotFoundException(String message) {
        super(message);
    }
}
