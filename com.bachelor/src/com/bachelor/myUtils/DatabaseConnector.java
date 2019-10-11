package com.bachelor.myUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    final static String DB_URL = "jdbc:postgresql://localhost:5432/com.bachelor";
    final static String USER = "postgres";
    final static String PASS = "0000";
    private static Connection conn;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            System.out.println("Connection failure.");
        }
        return conn;
    }
}
