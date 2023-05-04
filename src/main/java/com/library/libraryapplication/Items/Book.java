package com.library.libraryapplication.Items;

public class Book extends BookItem{
    int Pages;

    public Book(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice,
                String author, String genre, String ISBN, String language, String publisher, int year, int pages) {
        super(dayPrice, description, ID, loaned, name, overduePrice, author, genre, ISBN, language, publisher, year);
        Pages = pages;
    }
}
