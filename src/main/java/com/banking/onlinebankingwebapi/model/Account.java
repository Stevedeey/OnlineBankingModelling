package com.banking.onlinebankingwebapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;




public class Account {

    private String accountName;

    private String acountNumber;

    private Double balance;

    private String password;

    public Account() {

    }

    public Account(String accountName, String acountNumber, Double balance, String password) {
        this.accountName = accountName;
        this.acountNumber = acountNumber;
        this.balance = balance;
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAcountNumber() {
        return acountNumber;
    }

    public void setAcountNumber(String acountNumber) {
        this.acountNumber = acountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
