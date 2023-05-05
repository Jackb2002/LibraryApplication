package com.library.libraryapplication;

import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import com.library.libraryapplication.Users.User;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static void ClearTable(String name){
        try {
            conn.createStatement().execute("DELETE FROM " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void InsertUser(User user, Type type) {
        int ID = user.ID;
        String username = user.Username;
        String password = user.Password;

        //insert into user table
        try {
            conn.createStatement().execute("INSERT INTO users (id, username, password, admin, manager_id)" +
                    " VALUES (" + ID + ", '" + username + "', '" + password + "', " + (type == Administrator.class ? 1
                    : 0) + ", " + (user instanceof UnprivellagedUser ? ((UnprivellagedUser)user).Manager_ID : 0) + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get all users from database
    public static void LoadUsers() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<User>();
            List<Administrator> administrators = new ArrayList<Administrator>();
            List<UnprivellagedUser> unprivilegedUsers = new ArrayList<UnprivellagedUser>();
            while(rs.next()){
                int ID = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                users.add(new User(ID, username, password));

                boolean admin = rs.getString("admin") == "1";
                if(admin){
                    administrators.add(new Administrator(ID, username, password));
                }
                else{
                    int manager_id = rs.getInt("manager_id");

                    unprivilegedUsers.add(new UnprivellagedUser(ID, username, password,manager_id));
                }
            }
            User.Users = users.toArray(new User[0]);
            Administrator.Users = administrators.toArray(new Administrator[0]);
            UnprivellagedUser.Users = unprivilegedUsers.toArray(new UnprivellagedUser[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
