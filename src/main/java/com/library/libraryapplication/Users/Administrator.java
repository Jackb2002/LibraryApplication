package com.library.libraryapplication.Users;

public class Administrator extends User{
    User[] Team;

    public Administrator(String Username, String Password, User[] Team){
        super(Username, Password);
        this.Team = Team;
    }

    public void AddUser(User user){
        User[] temp = new User[Team.length + 1];
        for(int i = 0; i < Team.length; i++){
            temp[i] = Team[i];
        }
        temp[Team.length] = user;
        Team = temp;
    }

    public void RemoveUser(User user){
        User[] temp = new User[Team.length - 1];
        int j = 0;
        for(int i = 0; i < Team.length; i++){
            if(Team[i] != user){
                temp[j] = Team[i];
                j++;
            }
        }
        Team = temp;
    }

    public void AddItem(){
        //TODO
    }

    public void RemoveItem(){
        //TODO
    }

    public String GenerateReport(){
        //TODO
        return "";
    }

    public void EditItem(){
        //TODO
    }

    public void EditUser(){
        //TODO
    }
}
