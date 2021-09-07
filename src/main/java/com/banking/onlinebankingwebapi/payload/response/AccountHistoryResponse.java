package com.banking.onlinebankingwebapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryResponse {

    private Date transactionDate;
    private String transactionType;
    private String narration;
    private Double amount;
    private Double accountBalance;
}
