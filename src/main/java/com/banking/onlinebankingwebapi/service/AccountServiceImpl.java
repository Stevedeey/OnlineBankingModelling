package com.banking.onlinebankingwebapi.service;

import com.banking.onlinebankingwebapi.exception.ApiRequestException;
import com.banking.onlinebankingwebapi.exception.ApiResourceNotFoundException;
import com.banking.onlinebankingwebapi.model.Account;
import com.banking.onlinebankingwebapi.model.TransactionDetail;
import com.banking.onlinebankingwebapi.payload.auth.LoginRequest;
import com.banking.onlinebankingwebapi.payload.auth.LoginResponse;
import com.banking.onlinebankingwebapi.payload.request.CreateAccountRequest;
import com.banking.onlinebankingwebapi.payload.request.DepositRequest;
import com.banking.onlinebankingwebapi.payload.request.WithdrawalRequest;
import com.banking.onlinebankingwebapi.payload.response.*;
import com.banking.onlinebankingwebapi.security.jwt.JwtUtils;
import com.banking.onlinebankingwebapi.security.service.UserDetailsImpl;

import com.banking.onlinebankingwebapi.service.AccountDAOService.AccountHistoryDaoImpl;
import com.banking.onlinebankingwebapi.utility.Helper;
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

import java.util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final Double maximumDeposit = 1000000.0;
    private final Double minimumDeposit = 1.0;
    private final Double minimumBalance = 500.0;

    private HashMap<String, Account> accountMap = new HashMap<>();

    @Override
    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<String> nameList = new ArrayList<>();

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  JwtUtils jwtUtils;


    @Autowired
    private  AccountHistoryDaoImpl accountHistoryDao;


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

       log.info("NAMESTRING {}", userName);

        var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return  new LoginResponse(true, jwt);


    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {


        String accountNumber = Helper.generateAccountID();

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

        //setting account history


        response = new CreateAccountResponse();
        response.setMessage("Account successfully created NOW NOW"+ "Your new account number is "+ accountNumber);
        response.setSuccess(true);
        response.setResponseCode(200);


        return response;
    }

    public ResponseEntity<DepositResponse> deposit(DepositRequest depositRequest) {
        DepositResponse res = new DepositResponse();

        //getting account by accountNumber
        Account  userAccount  = getAccountMap().get(depositRequest.getAccountNumber());


        if (depositRequest.getAmount() >= maximumDeposit || depositRequest.getAmount() <= minimumDeposit)
            throw new ApiRequestException("Error depositing funds");

        if (userAccount != null) {

           //do Deposit
            Double newBalance = userAccount.getBalance() + depositRequest.getAmount();
            userAccount.setBalance(newBalance);

           var transactionDetail = getTransactionDetail(new TransactionDetail(),
                                                    depositRequest, newBalance);
           accountHistoryDao.save(transactionDetail);
           log.info(":::Got into the deposit and save transaction history");

        } else {
            throw new ApiResourceNotFoundException("Account does not exist!!!");
        }

        res.setMessage("Successfully deposited amount");
        res.setSuccess(true);
        res.setResponseCode(201);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }


        public WithdrawalResponse withdraw (WithdrawalRequest withdrawalRequest){

        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accountNumber = principal.getUsername();

        Account  userAccount  = getAccountMap().get(accountNumber);

        WithdrawalResponse response;

        if(!userAccount.getAcountNumber().equals(withdrawalRequest.getAccountNumber()) ){ //indicate that the user has not supplied his account or does not match

            response = new WithdrawalResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("Account does not exist! ");

            return response;
        }

        boolean match = bCryptPasswordEncoder.matches(withdrawalRequest.getAccountPassword(), principal.getPassword());

        if(!match){
            response = new WithdrawalResponse();
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("You are not authorized to withdraw from this account!");
            return response;

        }

        if(userAccount.getBalance() - withdrawalRequest.getWithdrawnAmount() < minimumBalance){
            response = new WithdrawalResponse();

            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage("You  must have a minimum of #500 in your account ");

            return response;
        }


        Double newBalance = userAccount.getBalance() - withdrawalRequest.getWithdrawnAmount();
        userAccount.setBalance(newBalance);

        //set transactionDetail
        Date currentDate = Helper.generateCurrentDate();

        TransactionDetail transactionDetail  =  new TransactionDetail();

        transactionDetail.setAccountNumber(withdrawalRequest.getAccountNumber());
        transactionDetail.setAmount(withdrawalRequest.getWithdrawnAmount());
        transactionDetail.setAccountBalance(newBalance);
        transactionDetail.setTransactionDate(currentDate);
        transactionDetail.setTransactionType("Withdrawal");
        transactionDetail.setNarration(withdrawalRequest.getNarration());

        accountHistoryDao.save(transactionDetail);

        response = new WithdrawalResponse();
        response.setMessage("Success Withdrawal");
        response.setSuccess(true);
        response.setResponseCode(200);

        log.info("Balance {} ", userAccount.getBalance());

        return response;

    }


    public ResponseEntity<AccountInfoResponse> getAccountInfo (String accountNumber){

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        AccountResponse accountResponse = new AccountResponse();

        Account account = accountMap.get(accountNumber);

        if(account != null){

            accountResponse.setAccountName(account.getAccountName());
            accountResponse.setAccountNumber(account.getAcountNumber());
            accountResponse.setBalance(account.getBalance());

            accountInfoResponse.setAccountResponse(accountResponse);
        }
        else {
            throw new ApiResourceNotFoundException("No user with this username found in our database!!");
        }

        accountInfoResponse.setMessage("Account info fetched succesfully!!");
        accountInfoResponse.setResponseCode(200);
        accountInfoResponse.setSuccess(true);

        return new ResponseEntity<>(accountInfoResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<List<AccountHistoryResponse>> getTransactionHistory(String accountNumber) {
        return new ResponseEntity<>(accountHistoryDao.getAccountHistory(accountNumber), HttpStatus.OK);
    }


    //::::::HELPER METHODS:::::::
    private static String getLoggedInUserName(){

        return   SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private  static TransactionDetail getTransactionDetail(TransactionDetail transactionDetail, DepositRequest depositRequest, Double newBalance){

        Date currentDate = Helper.generateCurrentDate();

        transactionDetail.setAccountNumber(depositRequest.getAccountNumber());
        transactionDetail.setAmount(depositRequest.getAmount());
        transactionDetail.setAccountBalance(newBalance);
        transactionDetail.setTransactionDate(currentDate);
        transactionDetail.setTransactionType("Deposit");
        transactionDetail.setNarration(depositRequest.getNarration());

        return  transactionDetail;
    }
}
