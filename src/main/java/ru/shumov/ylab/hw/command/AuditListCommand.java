package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class AuditListCommand extends AbstractCommand{
    private final String name = "audit list";
    private final String description = "audit list: User actions.";
    private final Role role = Role.ADMINISTRATOR;
    private final AuditService auditService;
    private final UserService userService;

    public AuditListCommand(AuditService auditService, UserService userService) {
        this.auditService = auditService;
        this.userService = userService;
    }

    public void execute() {
        var user = bootstrap.getUser();
        if(user.getStatus().equals(AuthorizationStatus.LOGOUT)) {
            System.out.println("User is not logged in.");
            return;
        }
        if(user.getRole().equals(Role.USER)) {
            System.out.println("Access denied.");
            return;
        }
        System.out.println("Enter username to see his actions:");
        String username= bootstrap.getScn().nextLine();
        if(userService.findOne(username) == null) {
            System.out.println("There is no user with this name.");
            return;
        }
        List<Operation> operations = auditService.getList(userService.findOne(username));
        for(Operation operation : operations) {
            System.out.println(operation.toString());
        }
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
