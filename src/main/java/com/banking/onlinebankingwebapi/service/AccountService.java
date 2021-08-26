package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.response.CreateAccountResponse;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface AccountService {

    Map<String, Account> getAccountMap();

    CreateAccountResponse createAccount (CreateAccountRequest createAccountRequest);

}
