package com.banking.onlinebankingwebapi.payload.response;

import com.banking.onlinebankingwebapi.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountInfoResponse  {

    private int responseCode;
    private boolean success;
    private String message;
    private AccountResponse accountResponse;

}
