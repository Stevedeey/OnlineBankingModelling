package com.banking.onlinebankingwebapi.payload.request;

import com.banking.onlinebankingwebapi.payload.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountInfoRequest {
    private int responseCode;
    private boolean success;
    private String message;
    private AccountResponse accountResponse;
}
