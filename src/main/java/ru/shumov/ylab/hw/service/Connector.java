package ru.shumov.ylab.hw.service;

import org.postgresql.Driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    public static void registerDriverJDBC() {
        final Driver driver = new Driver();
    }

    public static Connection connectionDB() {
        registerDriverJDBC();
        Connection connection = null;
        try {
            final File workingFolder = new File("src" + File.separator +
                    "main" + File.separator + "resources");
            final File properties = new File(workingFolder, "ConnectionDB.properties");
            final FileInputStream fileInputStream = new FileInputStream(properties);
            final Properties property = new Properties();
            property.load(fileInputStream);
            final String login = property.getProperty("db.login");
            final String password = property.getProperty("db.password");
            final String host = property.getProperty("db.host");
            connection = DriverManager.getConnection(host, login, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
