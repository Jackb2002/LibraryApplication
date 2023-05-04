package com.library.libraryapplication.Items;

public class Film extends Item{
    String Director;
    int Duration;
    String Format;
    String Resolution;

    public Film(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice,
                String director, int duration, String format, String resolution) {
        super(dayPrice, description, ID, loaned, name, overduePrice);
        Director = director;
        Duration = duration;
        Format = format;
        Resolution = resolution;
    }
}
