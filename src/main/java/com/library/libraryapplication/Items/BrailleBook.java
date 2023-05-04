package com.library.libraryapplication.Items;

public class BrailleBook extends BookItem{
    String Transcriber;


    public BrailleBook(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice,
                       String author, String genre, String ISBN, String language, String publisher, int year,
                       String Transcriber) {
        super(dayPrice, description, ID, loaned, name, overduePrice, author, genre, ISBN, language, publisher, year);
        this.Transcriber = Transcriber;
    }
}
