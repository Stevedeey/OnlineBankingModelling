package com.banking.onlinebankingwebapi.payload.response;

import com.banking.onlinebankingwebapi.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountStatementResponse {

    private Date transactionDate;

    private String transactionType;

    private String narration;

    private  Double amount;

    private Double accountBalance;

    private Account account;


}
