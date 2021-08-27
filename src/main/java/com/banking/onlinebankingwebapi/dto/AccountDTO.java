package com.banking.onlinebankingwebapi.dto;

import lombok.ToString;

@ToString
public class AccountDTO {
    String accountName;

    String acountNumber;

    Double balance;

    public AccountDTO(String accountName, String acountNumber, Double balance) {
        this.accountName = accountName;
        this.acountNumber = acountNumber;
        this.balance = balance;
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
}
