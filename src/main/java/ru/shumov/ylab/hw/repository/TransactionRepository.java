package ru.shumov.ylab.hw.repository;

import ru.shumov.ylab.hw.entity.Transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransactionRepository {
    private final Map<String, Transaction> transactionMap = new HashMap<>();
    public boolean checkKey(String id){
        return transactionMap.containsKey(id);
    }
    public Collection<Transaction> getList() {
        return transactionMap.values();
    }
    public Transaction findOne(String id) {
        return transactionMap.get(id);
    }
    public void merge(String id, Transaction transaction) {
        transactionMap.put(id, transaction);
    }
}
