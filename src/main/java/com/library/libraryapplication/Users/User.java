package com.library.libraryapplication.Users;

public class User {
    String Username;
    String Password;

    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public void ChangePassword(String Password){
        this.Password = Password;
    }

    public void Login(){
        System.out.println("Logged in as " + Username);
    }

    public void Logout(){
        System.out.println("Logged out from " + Username);
    }

}
