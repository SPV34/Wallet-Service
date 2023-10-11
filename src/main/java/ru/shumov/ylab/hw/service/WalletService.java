package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.Transaction;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.TransactionType;
import ru.shumov.ylab.hw.repository.TransactionRepository;


import java.util.ArrayList;
import java.util.Collection;

public class WalletService {
    private final TransactionRepository transactionRepository;

    public WalletService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean checkKey(String id){
        return transactionRepository.checkKey(id);
    }

    public ArrayList<Transaction> getList(User user) {
        Collection<Transaction> transactions = transactionRepository.getList();
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        for(Transaction transaction : transactions) {
            if(transaction.getUserId().equals(user.getUserID())){
                transactionArrayList.add(transaction);
            }
        }
        return transactionArrayList;
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
        transactionRepository.merge(id, transaction);
        user.setBalance(transaction.getAmount() + user.getBalance());
    }

    public void debit(User user, String id, String amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(id);
        transaction.setUserId(user.getUserID());
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setTransactionType(TransactionType.DEBIT);
        transactionRepository.merge(id, transaction);
        user.setBalance(user.getBalance() - transaction.getAmount());
    }

}
