package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.Md5Service;
import ru.shumov.ylab.hw.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class AuthorizationCommand extends AbstractCommand{

    private final String name = "log in";
    private final String description = "log in: Authorization.";
    private final Role role = Role.BOTH;
    private final UserService userService;
    private final Md5Service md5Service;
    private final AuditService auditService;

    public AuthorizationCommand(UserService userService, Md5Service md5Service,
                                AuditService auditService) {
        this.userService = userService;
        this.md5Service = md5Service;
        this.auditService = auditService;
    }
    public void execute() throws NoSuchAlgorithmException {
        var user = bootstrap.getUser();
        if(user.getStatus().equals(AuthorizationStatus.LOGIN)) {
            System.out.println("User has already been authorized.");
            return;
        }
        System.out.println("Enter username:");
        String username = bootstrap.getScn().nextLine();
        user = userService.findOne(username);
        if(user == null) {
            System.out.println("Invalid username.");
            return;
        }
        System.out.println("Enter password:");
        String password = bootstrap.getScn().nextLine();
        String passwordMd5 = md5Service.md5(password);
        if(passwordMd5.equals(user.getPassword())) {
            user.setStatus(AuthorizationStatus.LOGIN);
            bootstrap.setUser(user);
            System.out.println("Authorization successful.");
        }
        Operation operation = new Operation();
        operation.setOperationType(OperationType.AUTHORIZATION);
        operation.setDate(new Date());
        operation.setUserId(user.getUserID());
        auditService.create(operation);
        userService.update(user);
    }
    public Role getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {return name;}
}
