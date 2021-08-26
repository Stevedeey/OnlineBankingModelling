package com.banking.onlinebankingwebapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenericResponse {
    private int responseCode;

    private boolean success;

    private String message;

}
