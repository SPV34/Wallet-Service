package ru.shumov.ylab.hw.command;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.enums.OperationType;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.AuditService;
import ru.shumov.ylab.hw.service.CommandService;

import java.util.Collection;
import java.util.Date;

public class Help extends AbstractCommand{
    private final String name = "help";
    private final String description = "help : Output of available commands.";
    private final Role role = Role.BOTH;
    private final CommandService commandService;
    private final AuditService auditService;

    public Help(CommandService commandService, AuditService auditService) {
        this.commandService = commandService;
        this.auditService = auditService;
    }

    public void execute() {
        var user = bootstrap.getUser();
        Collection<AbstractCommand> commands = commandService.getList();
        if(user.getRole().equals(Role.ADMINISTRATOR)) {
            for(AbstractCommand command : commands) {
                if(command.getRole().equals(Role.ADMINISTRATOR) || command.getRole().equals(Role.BOTH)) {
                    System.out.println(command.getDescription());
                }
            }
        }
        if(user.getRole().equals(Role.USER)) {
            for(AbstractCommand command : commands) {
                if(command.getRole().equals(Role.USER) || command.getRole().equals(Role.BOTH)) {
                    System.out.println(command.getDescription());
                }
            }
        }
        Operation operation = new Operation();
        operation.setOperationType(OperationType.HELP);
        operation.setDate(new Date());
        operation.setUserId(user.getUserID());
        auditService.create(operation);
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
