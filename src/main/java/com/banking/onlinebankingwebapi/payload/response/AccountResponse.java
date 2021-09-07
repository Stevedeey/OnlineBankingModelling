package com.banking.onlinebankingwebapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountResponse {

    private String accountName;

    private String accountNumber;

    private Double balance;
}
