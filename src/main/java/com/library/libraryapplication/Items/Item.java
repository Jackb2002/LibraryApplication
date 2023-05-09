package com.library.libraryapplication.Items;

import com.library.libraryapplication.Database;

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
        //get time in format dd/mm/yyyy hh:mm:ss
        String time = java.time.LocalDate.now().toString();
        String due = java.time.LocalDate.now().plusDays(7).toString();
        Database.LoanItem(ID, time, due);
    }
    public void Return(){Database.ReturnItem(ID);}

    protected String GetIntoText() {
        return
                "Name: " + Name + "\n" +
                "Description: " + Description + "\n" +
                "DayPrice: " + DayPrice + "\n" +
                "OverduePrice: " + OverduePrice + "\n" +
                "ID: " + ID + "\n" +
                "Loaned: " + Loaned;
    }
}
