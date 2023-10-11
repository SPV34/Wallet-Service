package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.app.Bootstrap;
import ru.shumov.ylab.hw.enums.Role;

import java.security.NoSuchAlgorithmException;

public abstract class AbstractCommand {
    private String name;
    private String description;
    private Role role;
    protected Bootstrap bootstrap;


    public String getName() {
        return name;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public String getDescription() {
        return description;
    }

    public void execute() throws NoSuchAlgorithmException {
    }
    public Role getRole() {
        return role;
    }

}
