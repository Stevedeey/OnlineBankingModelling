package com.banking.onlinebankingwebapi.payload.request;


import lombok.ToString;

@ToString
public class DepositRequest {

    private String accountNumber;

    private Double amount;

    private String narration;

    public DepositRequest(String accountNumber, Double amount, String narration) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
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

