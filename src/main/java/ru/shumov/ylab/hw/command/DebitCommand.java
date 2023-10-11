package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.WalletService;

import java.util.Date;

public class DebitCommand extends AbstractCommand{
    private String name = "debit";
    private String description = "debit: Withdrawal of money from the account";
    private final Role role = Role.USER;
    private final WalletService walletService;
    private final AuditService auditService;

    public DebitCommand(WalletService walletService, AuditService auditService) {
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
        System.out.println("Enter Id of transaction:");
        String transactionId = bootstrap.getScn().nextLine();
        if (transactionId.isEmpty() || walletService.checkKey(transactionId)) {
            System.out.println("The transaction ID is not unique.");
            return;
        }
        System.out.println("Enter the amount of money you want to withdraw.");
        String amount = bootstrap.getScn().nextLine();
        if(amount.isEmpty() || amount.contains("-")){
            System.out.println("Incorrect debit amount.");
            return;
        }
        if(user.getBalance() - Double.parseDouble(amount) <= 0) {
            System.out.println("Transaction rejected, not enough money.");
            return;
        }
        walletService.debit(user, transactionId, amount);
        System.out.println("Done");

        Operation operation = new Operation();
        operation.setTransaction(walletService.findOne(transactionId));
        operation.setOperationType(OperationType.DEBIT);
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
