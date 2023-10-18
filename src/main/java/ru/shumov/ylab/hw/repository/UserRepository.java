package ru.shumov.ylab.hw.repository;

import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.AuthorizationStatus;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.service.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class UserRepository {
    private final int ID_FIELD = 1;
    private final int LOGIN_FIELD = 2;
    private final int PASSWORD_FIELD = 3;
    private final int BALANCE_FIELD = 4;
    private final int STATUS_FIELD = 5;
    private final int ROLE_FIELD = 6;

    private final Connection connection = Connector.connectionDB();
    @SneakyThrows
    public void persist(User user) {
        final String query = "INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUserID());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.setDouble(4, user.getBalance());
        statement.setString(5, user.getStatus().toString());
        statement.setString(6, user.getRole().toString());
        statement.executeUpdate();
        statement.close();
    }
    @SneakyThrows
    public void merge(User user) {
        final String query = "UPDATE users SET login=?, user_password=?, user_balance=?, user_status=?, user_role=? WHERE user_id=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setDouble(3, user.getBalance());
        statement.setString(4, user.getStatus().toString());
        statement.setString(5, user.getRole().toString());
        statement.setString(6, user.getUserID());
        statement.executeUpdate();
        statement.close();
    }
    @SneakyThrows
    public User findOne(String login) {
        final String query = "SELECT * FROM users WHERE login=?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        final ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        final User user = fetch(resultSet);
        statement.close();
        return user;
    }
    @SneakyThrows
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        final String query = "SELECT * FROM users";
        final PreparedStatement statement = connection.prepareStatement(query);
        final ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            users.add(fetch(resultSet));
        }
        statement.close();
        return users;
    }
    @SneakyThrows
    private User fetch(final ResultSet row) {
        try {
            if (row == null) return null;
            final User user = new User();
            user.setUserID(row.getString(ID_FIELD));
            user.setUsername(row.getString(LOGIN_FIELD));
            user.setPassword(row.getString(PASSWORD_FIELD));
            user.setBalance(row.getDouble(BALANCE_FIELD));
            user.setStatus(AuthorizationStatus.valueOf(row.getString(STATUS_FIELD).toUpperCase()));
            user.setRole(Role.valueOf(row.getString(ROLE_FIELD).toUpperCase()));
            return user;
        }
        catch (PSQLException e) {
            return null;
        }
    }
}
