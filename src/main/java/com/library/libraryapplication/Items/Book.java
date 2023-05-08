package com.library.libraryapplication.Items;

public class Book extends Item{
    public static Book[] Books;
    public int Pages;
    public String author;
    public String genre;
    public String ISBN;
    public String language;
    public String publisher;
    public int year;

    public Book(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice, String filename,
                String author, String genre, String ISBN, String language, String publisher, int year, int pages) {
        super(dayPrice, description, ID, loaned, name, overduePrice, filename);

        this.Pages = pages;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.language = language;
        this.publisher = publisher;
        this.year = year;
    }

    public String GetInfoText(){
        return super.GetIntoText() + "\n" +
                "Author: " + author + "\n" +
                "Genre: " + genre + "\n" +
                "ISBN: " + ISBN + "\n" +
                "Language: " + language + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + year + "\n" +
                "Pages: " + Pages + "\n" +
                "Description: " + Description;
    }
}
