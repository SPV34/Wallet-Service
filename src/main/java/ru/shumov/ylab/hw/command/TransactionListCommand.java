package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.Transaction;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.WalletService;

import java.util.ArrayList;
import java.util.Date;

public class TransactionListCommand extends AbstractCommand{
    private String name = "transaction list";
    private String description = "transaction list: Shows all user transactions.";
    private final Role role = Role.USER;
    private WalletService walletService;
    private final AuditService auditService;

    public TransactionListCommand(WalletService walletService, AuditService auditService) {
        this.walletService = walletService;
        this.auditService = auditService;
    }

    public void execute() {
        var user = bootstrap.getUser();
        if(user.getStatus().equals(AuthorizationStatus.LOGOUT)) {
            System.out.println("User is not logged in.");
            return;
        }
        if(!user.getRole().equals(Role.USER)) {
            System.out.println("Access denied.");
            return;
        }
        ArrayList<Transaction> transactionArrayList = walletService.getList(user);
        for (Transaction transaction : transactionArrayList) {
            System.out.println(transaction.toString());
        }
        Operation operation = new Operation();
        operation.setOperationType(OperationType.TRANSACTION_LIST);
        operation.setDate(new Date());
        operation.setUserId(user.getUserID());
        auditService.create(operation);
    }
    public Role getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
