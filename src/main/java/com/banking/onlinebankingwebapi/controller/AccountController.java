package com.banking.onlinebankingwebapi.controller;

import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.CreateAccountResponse;
import com.banking.onlinebankingwebapi.payload.response.WithdrawalResponse;
import com.banking.onlinebankingwebapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    final
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request){

        var account = service.createAccount(request);

        return  new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> doWithdrawal(@RequestBody WithdrawalRequest request){

        var withdrawal = service.withdraw(request);

        if(withdrawal.isSuccess() == false) return  new ResponseEntity<>(withdrawal, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}
