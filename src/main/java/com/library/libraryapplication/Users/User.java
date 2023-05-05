package com.library.libraryapplication.Users;

public class User {
    public static User[] Users;

    public int ID;
    public String Username;
    public String Password;

    public User(int ID, String Username, String Password){
        this.ID = ID;
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
