package com.library.libraryapplication.Users;

public class UnprivellagedUser extends User{
    public User Manager;

    public UnprivellagedUser(String Username, String Password, User Manager){
        super(Username, Password);
        this.Manager = Manager;
    }

}
