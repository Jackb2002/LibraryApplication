package com.library.libraryapplication.Items;

import java.lang.reflect.Type;

public class Item {
    public static Item[] Items;
    public double DayPrice;
    public String Description;
    public int ID;
    public boolean Loaned;
    public String Name;
    public double OverduePrice;

    ///For the image
    public String Filename;

    public Item(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice, String filename) {
        DayPrice = dayPrice;
        Description = description;
        this.ID = ID;
        Loaned = loaned;
        Name = name;
        OverduePrice = overduePrice;
        Filename = filename;
    }

    public void Loan(){
        Loaned = true;
    }
    public void Return(){
        Loaned = false;
    }


}
