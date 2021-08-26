package com.banking.onlinebankingwebapi.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {

  private boolean success;

  private String accessToken;
}
