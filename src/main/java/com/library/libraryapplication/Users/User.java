package com.library.libraryapplication.Users;

import java.util.ArrayList;

public class User {
    public static ArrayList<User> Users;
    public static User Current;
    public String Username;
    public String Password;
    public boolean Admin;

    public User(String Username, String Password, boolean Admin){
        this.Username = Username;
        this.Password = Password;
        this.Admin = Admin;
    }

    public void ChangePassword(String Password){
        this.Password = Password;
    }


}
