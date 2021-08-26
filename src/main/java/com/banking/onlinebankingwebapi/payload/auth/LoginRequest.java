package com.banking.onlinebankingwebapi.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {

    private String accountNumber;

    private String accountPassword;
}
