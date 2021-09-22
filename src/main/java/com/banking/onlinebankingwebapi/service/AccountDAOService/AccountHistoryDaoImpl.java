package com.banking.onlinebankingwebapi.service.AccountDAOService;

import com.banking.onlinebankingwebapi.model.TransactionDetail;
import com.banking.onlinebankingwebapi.payload.response.AccountHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AccountHistoryDaoImpl implements DAO<TransactionDetail, Map> {
    private Map<String, List<AccountHistoryResponse>> accountHistoryMap = new HashMap<>();

    @Override
    public Optional find(int index) {
        return Optional.ofNullable(accountHistoryMap.get(index));
    }

    @Override
    public Map findAll() {
        return accountHistoryMap;
    }


    @Override
    public TransactionDetail save(TransactionDetail transactionDetails) {

        AccountHistoryResponse accountHistoryResponse = new AccountHistoryResponse();

        log.info("::: I entered ELSE"+ accountHistoryMap);

        accountHistoryResponse.setAccountBalance(transactionDetails.getAccountBalance());
        accountHistoryResponse.setAmount(transactionDetails.getAmount());
        accountHistoryResponse.setTransactionDate(transactionDetails.getTransactionDate());
        accountHistoryResponse.setNarration(transactionDetails.getNarration());
        accountHistoryResponse.setTransactionType(transactionDetails.getTransactionType());


        if (accountHistoryMap.containsKey(transactionDetails.getAccountNumber())) {
            List<AccountHistoryResponse> infoList = accountHistoryMap.get(transactionDetails.getAccountNumber());
            infoList.add(accountHistoryResponse);

            accountHistoryMap.put(transactionDetails.getAccountNumber(), infoList);

        } else {
            List<AccountHistoryResponse> accountHistoryList =  new ArrayList<>();
            accountHistoryList.add(accountHistoryResponse);

            accountHistoryMap.put(transactionDetails.getAccountNumber(), accountHistoryList);
        }

        return transactionDetails;
    }

    @Override
    public void delete(TransactionDetail o) {
        accountHistoryMap.remove(o);
    }

    public List<AccountHistoryResponse> getAccountHistory (String accountNumber){
        log.error(":::Account History Map{}",accountHistoryMap.get(accountNumber));
        return accountHistoryMap.get(accountNumber) != null ? accountHistoryMap.get(accountNumber) : new ArrayList<>();
    }
}
