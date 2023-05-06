package com.library.libraryapplication.Items;

public class Film extends Item{
    public static Film[] Films;
    public String Director;
    public int Duration;
    public String Format;
    public String Resolution;

    public Film(double dayPrice, String description, int ID, boolean loaned, String name, double overduePrice, String filename,
                String director, int duration, String format, String resolution) {
        super(dayPrice, description, ID, loaned, name, overduePrice, filename);
        Director = director;
        Duration = duration;
        Format = format;
        Resolution = resolution;
    }
}
