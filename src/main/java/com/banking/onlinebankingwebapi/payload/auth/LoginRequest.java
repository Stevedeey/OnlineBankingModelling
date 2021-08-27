package com.banking.onlinebankingwebapi.payload.auth;

import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
public class LoginRequest {


    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountPassword;

    public LoginRequest(String accountNumber, String accountPassword) {
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
