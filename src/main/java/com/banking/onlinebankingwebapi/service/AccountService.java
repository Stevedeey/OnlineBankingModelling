package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.auth.LoginRequest;
import com.banking.onlinebankingwebapi.payload.auth.LoginResponse;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.CreateAccountResponse;
import com.banking.onlinebankingwebapi.payload.response.WithdrawalResponse;
import org.springframework.http.ResponseEntity;


import java.util.Map;


public interface AccountService {

    Map<String, Account> getAccountMap();

    CreateAccountResponse createAccount (CreateAccountRequest createAccountRequest);

    LoginResponse login(LoginRequest loginRequest);

   // ResponseEntity<WithdrawalResponse> withdraw (WithdrawalRequest request);

    WithdrawalResponse withdraw (WithdrawalRequest request);
}
