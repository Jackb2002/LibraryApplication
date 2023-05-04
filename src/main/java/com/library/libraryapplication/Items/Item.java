package com.library.libraryapplication.Items;

public class Item {
    double DayPrice;
    String Description;
    int ID;
    boolean Loaned;
    String Name;
    double OverduePrice;

    public Item(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice) {
        DayPrice = dayPrice;
        Description = description;
        this.ID = ID;
        Loaned = loaned;
        Name = name;
        OverduePrice = overduePrice;
    }

    public void Loan(){
        Loaned = true;
    }
    public void Return(){
        Loaned = false;
    }


}
