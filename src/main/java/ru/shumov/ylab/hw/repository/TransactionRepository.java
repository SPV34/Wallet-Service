package ru.shumov.ylab.hw.repository;

import lombok.SneakyThrows;
import ru.shumov.ylab.hw.entity.Transaction;
import ru.shumov.ylab.hw.enums.TransactionType;
import ru.shumov.ylab.hw.service.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class TransactionRepository {
    private final int ID_FIELD = 1;
    private final int USER_ID_FIELD = 2;
    private final int AMOUNT_FIELD = 3;
    private final int TYPE_FIELD = 4;
    private final Connection connection = Connector.connectionDB();
    @SneakyThrows
    public void persist(Transaction transaction) {
        final String query = "INSERT INTO users VALUES(?, ?, ?, ?)";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, transaction.getTransactionId());
        statement.setString(2, transaction.getUserId());
        statement.setDouble(3, transaction.getAmount());
        statement.setString(4, transaction.getTransactionType().toString());
        statement.executeUpdate();
        statement.close();
    }
    @SneakyThrows
    public boolean checkKey(String transaction_id){
        final String query = "SELECT * FROM transactions WHERE transaction_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, transaction_id);
        final ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        Transaction transaction = fetch(resultSet);
        if(transaction == null) {
            return false;
        }
        return true;
    }
    @SneakyThrows
    public List<Transaction> getList(String user_id) {
        List<Transaction> transactions = new ArrayList<>();
        final String query = "SELECT * FROM transactions WHERE user_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user_id);
        final ResultSet resultSet = statement.getResultSet();
        while(resultSet.next()){
            transactions.add(fetch(resultSet));
        }
        return transactions;
    }
    @SneakyThrows
    public Transaction findOne(String transaction_id) {
        final String query = "SELECT * FROM transactions WHERE transaction_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, transaction_id);
        final ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        return fetch(resultSet);
    }
    @SneakyThrows
    public void merge(String id, Transaction transaction) {
        final String query = "UPDATE transactions TO transaction_user_id=?, transaction_amount=?, transaction_type=? WHERE transaction_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, transaction.getUserId());
        statement.setDouble(2, transaction.getAmount());
        statement.setString(3, transaction.getTransactionType().toString());
        statement.setString(4, transaction.getTransactionId());
        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    private Transaction fetch(ResultSet row) {
        if(row == null) return null;
        final Transaction transaction = new Transaction();
        transaction.setTransactionId(row.getString(ID_FIELD));
        transaction.setUserId(row.getString(USER_ID_FIELD));
        transaction.setAmount(row.getDouble(AMOUNT_FIELD));
        transaction.setTransactionType(TransactionType.valueOf(row.getString(TYPE_FIELD)));
        return transaction;
    }
}
