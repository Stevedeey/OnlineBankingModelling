package com.banking.onlinebankingwebapi.service.AccountDAOService;

import com.banking.onlinebankingwebapi.model.TransactionDetail;

import java.util.Optional;

public interface DAO<T,M> {
    Optional<T> find(int index);
    M findAll();
    T save(T t);
    void delete(T t);
}
