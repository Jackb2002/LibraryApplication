package com.library.libraryapplication;

import com.library.libraryapplication.Database;
import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import com.library.libraryapplication.Users.User;

import java.util.Arrays;
import java.util.List;

public class DatabaseSerialiser {
    public static void SaveUsers(List<User> admins, List<User> users) {
        System.out.println("Saving users to database");
        System.out.println("Users: " + users.size());

        //Clear previous contents of database
        Database.ClearTable("users");

        //Insert new users
        for (User user : users) {
            if (user instanceof UnprivellagedUser) {
                Database.InsertUser(user, UnprivellagedUser.class);
            } else if (user instanceof Administrator) {
                Database.InsertUser(user, Administrator.class);
            }
        }
    }

    public static void LoadUsers(){
        System.out.println("Loading users from database");
        Database.LoadUsers();
        System.out.println("Loaded users");
    }
}
