package com.banking.onlinebankingwebapi.security.service;

import com.banking.onlinebankingwebapi.exception.ApiRequestException;
import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.service.AccountService;
import com.banking.onlinebankingwebapi.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    AccountService accountService;


    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {


        Account account = this.accountService.getAccountMap().get(accountNumber);

        if (account == null) {
            throw new ApiRequestException( accountNumber + " does not exist in our database");
        }
        return UserDetailsImpl.build(account);
    }
}
