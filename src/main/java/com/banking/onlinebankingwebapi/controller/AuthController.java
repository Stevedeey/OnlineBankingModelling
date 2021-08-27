package com.banking.onlinebankingwebapi.controller;

import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.auth.LoginRequest;
import com.banking.onlinebankingwebapi.payload.auth.LoginResponse;


import com.banking.onlinebankingwebapi.security.jwt.AuthTokenFilter;
import com.banking.onlinebankingwebapi.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {



    final
    AuthenticationManager authenticationManager;

    final
    AccountService accountService;

    public AuthController(AuthenticationManager authenticationManager, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest request){

       var loginUser =  accountService.login(request);


       return  new ResponseEntity<>(loginUser, HttpStatus.OK);
//        return  ResponseEntity.ok(loginUser);
    }


}
