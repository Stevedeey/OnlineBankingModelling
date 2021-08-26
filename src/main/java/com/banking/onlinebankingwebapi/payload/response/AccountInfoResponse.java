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
public class AccountInfoResponse extends GenericResponse {

    private Account account;

}
