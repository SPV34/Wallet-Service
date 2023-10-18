package ru.shumov.ylab.hw.entity;

import ru.shumov.ylab.hw.enums.OperationType;


import java.util.Date;
import java.util.UUID;
public class Operation {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private OperationType operationType;
    private Date date;

    private Transaction transaction;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", operationType=" + operationType +
                ", date=" + date +
                ", transaction=" + transaction +
                '}';
    }
}
