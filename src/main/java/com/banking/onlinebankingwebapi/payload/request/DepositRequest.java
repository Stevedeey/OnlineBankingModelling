package com.banking.onlinebankingwebapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepositRequest {

    private String accountNumber;

    private Double amount;

}
