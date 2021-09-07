package com.banking.onlinebankingwebapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDetail {
    private String accountNumber;

    private Date transactionDate;

    private String transactionType;

    private String narration;

    private Double amount;

    private Double accountBalance;
}
