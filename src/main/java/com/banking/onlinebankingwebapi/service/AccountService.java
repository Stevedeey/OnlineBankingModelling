package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.auth.LoginRequest;
import com.banking.onlinebankingwebapi.payload.auth.LoginResponse;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.DepositRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.*;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Map;


public interface AccountService {

    Map<String, Account> getAccountMap();
    CreateAccountResponse createAccount (CreateAccountRequest createAccountRequest);
    LoginResponse login(LoginRequest loginRequest);
    WithdrawalResponse withdraw (WithdrawalRequest request);
    ResponseEntity<DepositResponse> deposit(DepositRequest depositRequest);
    ResponseEntity<AccountInfoResponse> getAccountInfo (String accountNumber);
    ResponseEntity<List<AccountHistoryResponse>> getTransactionHistory(String accountNumber);
}

