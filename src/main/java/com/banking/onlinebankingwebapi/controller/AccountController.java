package com.banking.onlinebankingwebapi.controller;

import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.DepositRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.*;
import com.banking.onlinebankingwebapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    final
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(path = "account_info/{accountNumber}")
    public ResponseEntity<AccountInfoResponse> getAccountInfo(@PathVariable String accountNumber){
        return service.getAccountInfo(accountNumber);
    }

    @GetMapping(path = "account_statement/{accountNumber}")
    public ResponseEntity<List<AccountHistoryResponse>> getTransactionHistory(@PathVariable String accountNumber){
        return service.getTransactionHistory(accountNumber);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request){

        var account = service.createAccount(request);

        return  new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "/withdraw")
    public ResponseEntity<?> doWithdrawal(@RequestBody WithdrawalRequest request){

        var withdrawal = service.withdraw(request);

        if(withdrawal.isSuccess() == false) return  new ResponseEntity<>(withdrawal, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PostMapping(path = "/deposit")
    public ResponseEntity<DepositResponse> doDeposit(@RequestBody DepositRequest depositRequest){

        return service.deposit(depositRequest);
    }


}
