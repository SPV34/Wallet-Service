package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.UserService;

import java.util.Collection;
import java.util.List;

public class UserListCommand extends AbstractCommand{
    private final String name = "user list";
    private final String description = "user list: Shows all users.";
    private final Role role = Role.ADMINISTRATOR;
    private final UserService userService;

    public UserListCommand(UserService userService) {
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
        List<User> userCollection = userService.findAll();
        for(User userFromCollection : userCollection) {
            System.out.println(userFromCollection);
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
