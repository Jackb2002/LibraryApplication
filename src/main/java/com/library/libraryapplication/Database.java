package com.library.libraryapplication;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection conn;
    public static void Init(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        File databaseFile = new File("database.db");
        String path = databaseFile.getAbsolutePath();
        System.out.println("Opening database at "+ path);
        boolean databaseExists = databaseFile.exists();

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean Login(String user, String pass) {
        try {
            return conn.createStatement().executeQuery("SELECT * FROM users WHERE username = '" + user + "'" +
                    " AND password = '" + pass + "'").next();
        } catch (SQLException e) {
            return false;
        }
    }

    public String Query(String Query){
        try {
            return conn.createStatement().executeQuery(Query).toString();
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
