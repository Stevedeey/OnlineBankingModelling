package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.exception.ApiRequestException;
import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.response.CreateAccountResponse;
import com.banking.onlinebankingwebapi.utility.GenerateAcountID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private HashMap<String, Account> accountMap = new HashMap<>();

    @Override
    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<String> nameList = new ArrayList<>();

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        String accountNumber = GenerateAcountID.generateID();

        CreateAccountResponse response = new CreateAccountResponse();

        if(nameList.contains(createAccountRequest.getAccountName())){

            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("User with this name already exist");
            return response;
        }

        if(accountMap.containsKey(accountNumber)){
          createAccount(createAccountRequest);
        }


        if (createAccountRequest.getInitialDeposit() < 500) {

            throw new ApiRequestException("You cannot deposit less than 500 ");
        }


        Account account = new Account();
        account.setAccountName(createAccountRequest.getAccountName());
        account.setAcountNumber(accountNumber);
        account.setPassword(bCryptPasswordEncoder.encode(createAccountRequest.getAccountPassword()));

        if (accountMap == null) accountMap = new HashMap<>();

        accountMap.put(accountNumber, account);
        nameList.add(createAccountRequest.getAccountName());


        response.setMessage("Account successfully created "+ "Your new account number is "+ accountNumber);
        response.setSuccess(true);
        response.setResponseCode(200);

        return response;
    }
}
