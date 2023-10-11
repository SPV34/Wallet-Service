package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;

import java.util.Date;

public class ShowBalanceCommand extends AbstractCommand{
    private final String name = "show balance";
    private final String description = "show balance: Shows the user's balance.";
    private final Role role = Role.USER;
    private final AuditService auditService;

    public ShowBalanceCommand(AuditService auditService) {
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
        System.out.println("Your balance: " + user.getBalance());

        Operation operation = new Operation();
        operation.setOperationType(OperationType.SHOW_BALANCE);
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
