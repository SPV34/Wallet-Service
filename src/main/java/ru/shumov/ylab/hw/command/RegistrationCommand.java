package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.Md5Service;
import ru.shumov.ylab.hw.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class RegistrationCommand extends AbstractCommand{
    private final String name = "registration";
    private final String description = "registration: Registration new user.";
    private final Role role = Role.USER;
    private final UserService userService;
    private final Md5Service md5Service;
    private final AuditService auditService;

    public RegistrationCommand(UserService userService, Md5Service md5Service, AuditService auditService) {
        this.userService = userService;
        this.md5Service = md5Service;
        this.auditService = auditService;
    }

    public void execute () throws NoSuchAlgorithmException {
        var userFromBp = bootstrap.getUser();
        if(userFromBp.getStatus().equals(AuthorizationStatus.LOGIN)) {
            System.out.println("Access denied.");
            return;
        }
        System.out.println("Enter username:");
        String username = bootstrap.getScn().nextLine();
        if(username == null || username.isEmpty()) {
            System.out.println("Invalid username.");
            return;
        }
        if(userService.findOne(username) != null) {
            System.out.println("User with this name already exist.");
            return;
        }
        User user = new User();
        System.out.println("Enter password:");
        String password = bootstrap.getScn().nextLine();
        if(password == null || password.isEmpty()) {
            System.out.println("Invalid password.");
            return;
        }
        String passwordMd5 = md5Service.md5(password);

        user.setUsername(username);
        user.setPassword(passwordMd5);
        userService.create(username, user);
        System.out.println("Registration successful.");

        Operation operation = new Operation();
        operation.setOperationType(OperationType.REGISTRATION);
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

    public String getName() {return name;}
}
