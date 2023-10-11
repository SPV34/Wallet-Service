package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;

import java.util.Date;

public class LogOutCommand extends AbstractCommand{
    private final String name = "log out";
    private final String description = "log out: log out of your account";
    private final AuditService auditService;
    private final Role role = Role.BOTH;

    public LogOutCommand(AuditService auditService) {
        this.auditService = auditService;
    }

    public void execute() {
        var user = bootstrap.getUser();
        if(user.getStatus().equals(AuthorizationStatus.LOGOUT)) {
            System.out.println("You are not logged in.");
            return;
        }
        Operation operation = new Operation();
        operation.setOperationType(OperationType.LOGOUT);
        operation.setDate(new Date());
        operation.setUserId(user.getUserID());
        auditService.create(operation);

        bootstrap.setUser(new User());
        System.out.println("You have successfully logged out of your account.");

    }
    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
