package com.banking.onlinebankingwebapi.payload.request;


import lombok.ToString;

@ToString
public class DepositRequest {

    private String accountNumber;

    private Double amount;

    public DepositRequest(String accountNumber, Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

