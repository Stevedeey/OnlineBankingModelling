package com.banking.onlinebankingwebapi.exception.unauthorised;


import com.banking.onlinebankingwebapi.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class WrongCredentialExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(UnauthorizedRequestException e){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;




        UnauthorizedException unauthorizedException = new UnauthorizedException(e.getMessage(),
                false,unauthorized,ZonedDateTime.now(ZoneId.of("Z")));


        return new ResponseEntity<>(unauthorizedException, unauthorized);
    }
}
