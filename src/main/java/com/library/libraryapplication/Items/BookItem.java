package com.library.libraryapplication.Items;

public class BookItem extends Item{
    //Containing author, genre, isbn, launguage, publisher and year

    String Author;
    String Genre;
    String ISBN;
    String Language;
    String Publisher;
    int Year;

    public BookItem(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice,
                    String author, String genre, String ISBN, String language, String publisher, int year) {
        super(dayPrice, description, ID, loaned, name, overduePrice);
        Author = author;
        Genre = genre;
        this.ISBN = ISBN;
        Language = language;
        Publisher = publisher;
        Year = year;
    }
}
