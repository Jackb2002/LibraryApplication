package com.library.libraryapplication.Items;

public class AudioBook extends Book{
    public static AudioBook[] AudioBooks;
    //Containing duration, narrator, and format

    public String duration;
    public String narrator;
    public String format;

    public AudioBook(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice, String filename,
                     String author, String genre, String ISBN, String language, String publisher, int year,
                     String duration, String narrator, String format) {
        super(dayPrice, description, ID, loaned, name, overduePrice,filename, author, genre, ISBN, language, publisher, year, 0);
        this.duration = duration;
        this.narrator = narrator;
        this.format = format;
    }

}
