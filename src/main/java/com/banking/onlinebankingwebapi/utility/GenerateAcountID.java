package com.banking.onlinebankingwebapi.utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateAcountID {

    public static String generateID(){

        Random rand = new Random();
        int generatedID = rand.nextInt((int) 10000000000L);

        return String.valueOf(generatedID);
    }
}
