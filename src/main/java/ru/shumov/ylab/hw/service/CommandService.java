package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.app.Bootstrap;
import ru.shumov.ylab.hw.command.*;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

public class CommandService {
    private final Bootstrap bootstrap;
    private final Map<String, AbstractCommand> commands = new HashMap<>();

    public CommandService(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Collection<AbstractCommand> getList() {
        return commands.values();
    }

    public boolean checkKey(final String key) {
        return commands.containsKey(key);
    }

    public void commandExecute(final String key) throws NoSuchAlgorithmException {
        commands.get(key).execute();
    }

    public void commandsInit() {
        AuthorizationCommand authorizationCommand = new AuthorizationCommand(bootstrap.getUserService(), bootstrap.getMd5Service(),
                bootstrap.getAuditService());
        authorizationCommand.setBootstrap(bootstrap);
        RegistrationCommand registrationCommand = new RegistrationCommand(bootstrap.getUserService(), bootstrap.getMd5Service(),
                bootstrap.getAuditService());
        registrationCommand.setBootstrap(bootstrap);
        CreditCommand creditCommand = new CreditCommand(bootstrap.getWalletService(), bootstrap.getAuditService());
        creditCommand.setBootstrap(bootstrap);
        DebitCommand debitCommand = new DebitCommand(bootstrap.getWalletService(), bootstrap.getAuditService());
        debitCommand.setBootstrap(bootstrap);
        TransactionListCommand transactionListCommand = new TransactionListCommand(bootstrap.getWalletService(), bootstrap.getAuditService());
        transactionListCommand.setBootstrap(bootstrap);
        ShowBalanceCommand showBalanceCommand = new ShowBalanceCommand(bootstrap.getAuditService());
        showBalanceCommand.setBootstrap(bootstrap);
        AuditListCommand auditListCommand = new AuditListCommand(bootstrap.getAuditService(), bootstrap.getUserService());
        auditListCommand.setBootstrap(bootstrap);
        Exit exit = new Exit();
        exit.setBootstrap(bootstrap);
        Help help = new Help(bootstrap.getCommandService(), bootstrap.getAuditService());
        help.setBootstrap(bootstrap);
        LogOutCommand logOutCommand = new LogOutCommand(bootstrap.getAuditService());
        logOutCommand.setBootstrap(bootstrap);
        UserListCommand userListCommand = new UserListCommand(bootstrap.getUserService());
        userListCommand.setBootstrap(bootstrap);
        commands.put(authorizationCommand.getName(), authorizationCommand);
        commands.put(registrationCommand.getName(), registrationCommand);
        commands.put(showBalanceCommand.getName(), showBalanceCommand);
        commands.put(creditCommand.getName(), creditCommand);
        commands.put(debitCommand.getName(), debitCommand);
        commands.put(transactionListCommand.getName(), transactionListCommand);
        commands.put(auditListCommand.getName(),auditListCommand);
        commands.put(exit.getName(), exit);
        commands.put(help.getName(), help);
        commands.put(logOutCommand.getName(), logOutCommand);
        commands.put(userListCommand.getName(), userListCommand);
    }
}
