package com.library.libraryapplication.Users;

public class UnprivellagedUser extends User{

    public static UnprivellagedUser[] Users;
    public int Manager_ID;


    public UnprivellagedUser(int ID, String Username, String Password, int Manager){
        super(ID, Username, Password);
        Manager_ID = Manager;
    }

}
