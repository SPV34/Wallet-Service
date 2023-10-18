package ru.shumov.ylab.hw.app;

import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.repository.AuditRepository;
import ru.shumov.ylab.hw.repository.TransactionRepository;
import ru.shumov.ylab.hw.repository.UserRepository;
import ru.shumov.ylab.hw.service.*;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Bootstrap {
    private User user = new User();
    private final Md5Service md5Service = new Md5Service();
    private final UserRepository userRepository = new UserRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final AuditRepository auditRepository = new AuditRepository();
    private final UserService userService = new UserService(userRepository, md5Service);
    private final AuditService auditService = new AuditService(auditRepository);
    private final CommandService commandService = new CommandService(this);
    private final WalletService walletService = new WalletService(transactionRepository);
    private final Scanner scn = new Scanner(System.in);
    private boolean work = true;
    public void init() throws NoSuchAlgorithmException {
        commandService.commandsInit();
        start();
    }

    public void start() {
        System.out.println("Welcome to Wallet Service.");
        System.out.println("Enter help to see commands.");
        while (work) {
            try {
                String commandName = scn.nextLine();
                if (commandService.checkKey(commandName)) {
                    commandService.commandExecute(commandName);
                } else {
                    System.out.println("Command does not exist");
                }
            }
            catch (Exception exception) {
                System.out.println("An error occurred while the application was running.");
                exception.printStackTrace();
            }
        }
    }

    public void setWork(boolean work) {this.work = work;}
    public User getUser() {return user;}
    public Scanner getScn() {return scn;}
    public void setUser(User newUser) {this.user = newUser;}
    public Md5Service getMd5Service() {return md5Service;}

    public UserService getUserService() {
        return userService;
    }

    public AuditService getAuditService() {
        return auditService;
    }

    public WalletService getWalletService() {
        return walletService;
    }

    public CommandService getCommandService() {
        return commandService;
    }
}