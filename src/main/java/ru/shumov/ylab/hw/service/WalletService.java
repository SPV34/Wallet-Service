package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.Transaction;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.TransactionType;
import ru.shumov.ylab.hw.repository.TransactionRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WalletService {
    private final TransactionRepository transactionRepository;

    public WalletService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean checkKey(String id){
        return transactionRepository.checkKey(id);
    }

    public List<Transaction> getList(User user) {
        return transactionRepository.getList(user.getUserID());
    }

    public Transaction findOne(String id) {
        return transactionRepository.findOne(id);
    }
    public void credit(User user, String id, String amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setUserId(user.getUserID());
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setTransactionType(TransactionType.CREDIT);
        transactionRepository.persist(transaction);
        user.setBalance(transaction.getAmount() + user.getBalance());
    }

    public void debit(User user, String id, String amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setUserId(user.getUserID());
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setTransactionType(TransactionType.DEBIT);
        transactionRepository.persist(transaction);
        user.setBalance(user.getBalance() - transaction.getAmount());
    }

}
