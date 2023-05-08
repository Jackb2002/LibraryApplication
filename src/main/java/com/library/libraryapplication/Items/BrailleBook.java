package com.library.libraryapplication.Items;

public class BrailleBook extends Book{
    public static BrailleBook[] BrailleBooks;
    public String Transcriber;


    public BrailleBook(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice, String filename,
                       String author, String genre, String ISBN, String language, String publisher, int year, int pages,
                       String Transcriber) {
        super(dayPrice, description, ID, loaned, name, overduePrice, filename, author, genre, ISBN, language, publisher, year
                , pages);
        this.Transcriber = Transcriber;
    }

    public String GetInfoText(){
        return super.GetInfoText() + "\nTranscriber: " + Transcriber;
    }
}
