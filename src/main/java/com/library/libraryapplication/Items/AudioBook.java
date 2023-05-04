package com.library.libraryapplication.Items;

public class AudioBook extends BookItem{
    //Containing duration, narrator, and format

    String duration;
    String narrator;
    String format;

    public AudioBook(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice,
                     String author, String genre, String ISBN, String language, String publisher, int year,
                     String duration, String narrator, String format) {
        super(dayPrice, description, ID, loaned, name, overduePrice, author, genre, ISBN, language, publisher, year);
        this.duration = duration;
        this.narrator = narrator;
        this.format = format;
    }

}
