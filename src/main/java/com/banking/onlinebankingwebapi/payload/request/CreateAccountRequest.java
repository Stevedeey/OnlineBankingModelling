package com.banking.onlinebankingwebapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
public class CreateAccountRequest {
    private String accountName;

    private String accountPassword;

    private Double initialDeposit;

    public CreateAccountRequest() {
        initialDeposit = 0.0;
    }

    public CreateAccountRequest(String accountName, String accountPassword, Double initialDeposit) {
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.initialDeposit = initialDeposit;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}
