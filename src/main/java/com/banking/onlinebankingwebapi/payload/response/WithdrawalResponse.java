package com.banking.onlinebankingwebapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WithdrawalResponse{
    private int responseCode;

    private boolean success;

    private String message;
}
