package ru.shumov.ylab.hw.entity;

import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.Role;

import java.util.UUID;

public class User {

    private String username;
    private String password;
    private double balance;
    private String userID = String.valueOf(UUID.randomUUID());
    private AuthorizationStatus status = AuthorizationStatus.LOGOUT;
    private  Role role = Role.USER;
    public String getUsername() {return username;}
    public void setUsername(String userName) {this.username = userName;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
    public AuthorizationStatus getStatus() {return status;}
    public void setStatus(AuthorizationStatus status) {this.status = status;}
    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return role;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", userID='" + userID + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
