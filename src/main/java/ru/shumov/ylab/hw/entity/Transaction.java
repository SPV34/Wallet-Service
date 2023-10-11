package ru.shumov.ylab.hw.entity;

import ru.shumov.ylab.hw.enums.TransactionType;

public class Transaction {

    private String transactionId;
    private String userId;
    private double amount;
    private TransactionType transactionType;
    public String getTransactionId() {return transactionId;}
    public void setTransactionId(String transactionId) {this.transactionId = transactionId;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}
    public TransactionType getTransactionType() {return transactionType;}
    public void setTransactionType(TransactionType transactionType) {this.transactionType = transactionType;}

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                '}';
    }
}
