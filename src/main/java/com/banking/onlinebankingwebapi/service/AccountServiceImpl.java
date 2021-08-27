package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.exception.ApiRequestException;
import com.banking.onlinebankingwebapi.exception.unauthorised.UnauthorizedRequestException;
import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.payload.auth.LoginRequest;
import com.banking.onlinebankingwebapi.payload.auth.LoginResponse;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.CreateAccountResponse;
import com.banking.onlinebankingwebapi.payload.response.WithdrawalResponse;
import com.banking.onlinebankingwebapi.security.jwt.JwtUtils;
import com.banking.onlinebankingwebapi.security.service.UserDetailsImpl;
import com.banking.onlinebankingwebapi.utility.GenerateAcountID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    UserDetailsService userDetailsService;


    @Override
        public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                    loginRequest.getAccountNumber(),
                    loginRequest.getAccountPassword()));


        } catch (BadCredentialsException e) {

            throw new ApiRequestException("Wrong Credential!!!");

        }

        //context holder holding the authentication object for future validation and authorization
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // UserDetails userDetails1 =  userDetailsService.loadUserByUsername(loginRequest.getUsername());
        //alternative to the line above to retrieve userdetails

       var userName = SecurityContextHolder.getContext().getAuthentication().getName();
       log.error("NAMESTRING {}", userName);

        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return  new LoginResponse(true, jwt);



    }


    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        String accountNumber = GenerateAcountID.generateID();

        CreateAccountResponse response;

        if(nameList.contains(createAccountRequest.getAccountName())){
            response = new CreateAccountResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("User with this name already exist");
            return response;
        }

        if(accountMap.containsKey(accountNumber)){
          createAccount(createAccountRequest);
        }


        if (createAccountRequest.getInitialDeposit() < 500) {

            response = new CreateAccountResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("Your initial deposit cannot be less than 500 ");
            return response;

        }


        Account account = new Account();
        account.setAccountName(createAccountRequest.getAccountName());
        account.setAcountNumber(accountNumber);
        account.setBalance(createAccountRequest.getInitialDeposit());
        account.setPassword(bCryptPasswordEncoder.encode(createAccountRequest.getAccountPassword()));



        if (accountMap == null) accountMap = new HashMap<>();

        accountMap.put(accountNumber, account);
        nameList.add(createAccountRequest.getAccountName());

        response = new CreateAccountResponse();
        response.setMessage("Account successfully created "+ "Your new account number is "+ accountNumber);
        response.setSuccess(true);
        response.setResponseCode(200);


        return response;
    }

    public WithdrawalResponse withdraw (WithdrawalRequest request){



        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accountNumber = principal.getUsername();

        Account  userAccount  = getAccountMap().get(accountNumber);

        WithdrawalResponse response;

        if(!userAccount.getAcountNumber().equals(request.getAccountNumber()) ){ //indicate that the user has not supplied his account or does not match

            response = new WithdrawalResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("Account does not exist!!!");

            return response;
        }

        boolean match = bCryptPasswordEncoder.matches(request.getAccountPassword(), principal.getPassword());

        if(!match){
            response = new WithdrawalResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("You are not authorized to withdraw from this account!");
            return response;

        }


        if(userAccount.getBalance() - request.getWithdrawnAmount() < 500){
            response = new WithdrawalResponse();

            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("You  must have a minimum of #500 in your account ");

            return response;
        }

        if (request.getWithdrawnAmount() < 1){

            response = new WithdrawalResponse();

            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("Negative amount cannot be withdrawn");

            return response;
        }



        userAccount.setBalance(userAccount.getBalance() - request.getWithdrawnAmount());
        response = new WithdrawalResponse();
        response.setMessage("Success Withdrawal");
        response.setSuccess(true);
        response.setResponseCode(200);

        log.info("Balance {} ", userAccount.getBalance());

        return response;

    }

//    public ResponseEntity<WithdrawalResponse> withdraw (WithdrawalRequest request){
//
//        Account  userAccount  = getAccountMap().get(getLoggedInUserName());
//
//        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        WithdrawalResponse response = new WithdrawalResponse();
//
//        if(userAccount == null ){
//            response.setResponseCode(400);
//            response.setSuccess(false);
//            response.setMessage("Account does not exist!!!");
//            return new ResponseEntity<WithdrawalResponse>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        boolean match = bCryptPasswordEncoder.matches(request.getAccountPassword(), principal.getPassword());
//
//        if(!match){
//
//            response.setResponseCode(400);
//            response.setSuccess(false);
//            response.setMessage("You are not authorized to withdraw from this account!");
//            return new ResponseEntity<WithdrawalResponse>(response, HttpStatus.BAD_REQUEST);
//
//        }
//
//
//        if(userAccount.getBalance() - request.getWithdrawnAmount() < 500){
//
//            response.setResponseCode(400);
//            response.setSuccess(false);
//            response.setMessage("You  must have a minimum of #500 in your account ");
//
//            return new ResponseEntity<WithdrawalResponse>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        if (request.getWithdrawnAmount() < 1){
//
//            response.setResponseCode(400);
//            response.setSuccess(false);
//            response.setMessage("Negative amount cannot be withdrawn");
//
//            return new ResponseEntity<WithdrawalResponse>(response, HttpStatus.BAD_REQUEST);
//        }
//
//
//
//            userAccount.setBalance(userAccount.getBalance() - request.getWithdrawnAmount());
//            response.setMessage("Success Withdrawal");
//            response.setSuccess(true);
//            response.setResponseCode(200);
//
//            return new ResponseEntity<WithdrawalResponse>(response, HttpStatus.OK);
//
//    }

    private static String getLoggedInUserName(){

        return   SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
