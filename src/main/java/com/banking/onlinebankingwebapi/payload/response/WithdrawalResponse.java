package com.banking.onlinebankingwebapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawalResponse {
    String accountNumber;

    String accountPassword;

    Double withdrawnAmount;
}
