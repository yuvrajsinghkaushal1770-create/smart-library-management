package com.library.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            Properties props = new Properties();
            InputStream in = new FileInputStream("config.properties");
            props.load(in);
            in.close();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database.");
        } catch (Exception e) {
            System.out.println("DB connection failed: " + e.getMessage());
        }
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}