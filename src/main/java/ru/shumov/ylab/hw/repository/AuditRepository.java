package ru.shumov.ylab.hw.repository;

import lombok.SneakyThrows;
import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.Transaction;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.TransactionType;
import ru.shumov.ylab.hw.service.Connector;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AuditRepository {
    private final int ID_FIELD = 1;
    private final int USER_ID_FIELD = 2;
    private final int TYPE_FIELD = 3;
    private final int DATE_FIELD = 4;
    private final int TRANSACTION_ID_FIELD = 5;
    private final int TRANSACTION_AMOUNT_FIELD = 6;
    private final int TRANSACTION_TYPE_FIELD = 7;
    private final Connection connection = Connector.connectionDB();
    @SneakyThrows
    public void persist(Operation operation){
        if (operation.getTransaction() == null) {
            final String query = "INSERT INTO operations VALUES(?, ?, ?, ?)";
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, operation.getId());
            statement.setString(2, operation.getUserId());
            statement.setString(3, operation.getOperationType().toString());
            statement.setDate(4, new java.sql.Date(operation.getDate().getTime()));
            statement.executeUpdate();
            statement.close();
        } else {
            final String query = "INSERT INTO operations VALUES(?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, operation.getId());
            statement.setString(2, operation.getUserId());
            statement.setString(3, operation.getOperationType().toString());
            statement.setDate(4, new java.sql.Date(operation.getDate().getTime()));
            statement.setString(5, operation.getTransaction().getTransactionId());
            statement.setDouble(6, operation.getTransaction().getAmount());
            statement.setString(7, operation.getTransaction().getTransactionType().toString());
            statement.executeUpdate();
            statement.close();
        }
    }
    @SneakyThrows
    public List<Operation> getList(String user_id) {
        final List<Operation> operations = new ArrayList<>();
        final String query = "SELECT * FROM operations WHERE user_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user_id);
        final ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            operations.add(fetch(resultSet));
        }
        statement.close();
        return operations;
    }
    @SneakyThrows
    public Operation fetch(ResultSet row) {
        if(row == null) return null;
        final Operation operation = new Operation();
        final Transaction transaction = new Transaction();
        operation.setId(row.getString(ID_FIELD));
        operation.setUserId(row.getString(USER_ID_FIELD));
        operation.setOperationType(OperationType.valueOf(row.getString(TYPE_FIELD).toUpperCase()));
        operation.setDate(row.getDate(DATE_FIELD));
        transaction.setTransactionId(row.getString(TRANSACTION_ID_FIELD));
        transaction.setAmount(row.getDouble(TRANSACTION_AMOUNT_FIELD));
        if(row.getString(TRANSACTION_TYPE_FIELD) == null) {
            transaction.setTransactionType(null);
        } else {
            transaction.setTransactionType(TransactionType.valueOf(row.getString(TRANSACTION_TYPE_FIELD).toUpperCase()));
        }
        transaction.setUserId(operation.getUserId());
        operation.setTransaction(transaction);
        return operation;
    }
}
