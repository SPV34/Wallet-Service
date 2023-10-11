package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.enums.Role;

public class Exit extends AbstractCommand{
    private final String name = "exit";
    private final String description = "exit: Stopping the program.";
    private final Role role = Role.BOTH;

    public void execute() {
        bootstrap.setWork(false);
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
