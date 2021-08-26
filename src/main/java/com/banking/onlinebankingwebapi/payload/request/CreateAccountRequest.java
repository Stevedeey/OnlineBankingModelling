package com.banking.onlinebankingwebapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CreateAccountRequest {
    private String accountName;

    private String accountPassword;

    private Double initialDeposit;

    public CreateAccountRequest() {
        initialDeposit = 0.0;
    }
}
