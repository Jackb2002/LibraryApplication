package com.library.libraryapplication;

import com.library.libraryapplication.Items.*;
import com.library.libraryapplication.Users.User;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    public static Connection conn;
    public static void Init(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        File databaseFile = new File("database.db");
        String path = databaseFile.getAbsolutePath();
        System.out.println("Opening database at "+ path);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LoanItem(int ID, String time, String due){
        System.out.println("LoanItem: " + ID + ", " + time);
        try {
            conn.createStatement().execute("INSERT INTO loan_times (id, loan_time, due_time) VALUES (" + ID + "," +
                    " " + "'" + time + "', '" + due + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long GetLoanTime(int ID){
        try{
            return conn.createStatement().executeQuery("SELECT * FROM loan_times WHERE id = " + ID)
                    .getLong("loan_time");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ReturnItem(int ID){
        Query("DELETE FROM loan_times WHERE id = " + ID);
    }

    public static boolean Login(String user, String pass) {
        try {
            return conn.createStatement().executeQuery("SELECT * FROM users WHERE username = '" + user + "'" +
                    " AND password = '" + pass + "'").next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static void ClearTable(String name){
        Query("DELETE FROM " + name);
    }


    public static void InsertUser(User user) {
        String username = user.Username;
        String password = user.GetPassword();
        Boolean Admin = user.Admin;

        //insert into user table
        try {
            conn.createStatement().execute("INSERT INTO users (username, password, admin) VALUES ('"
                    + username + "', '" + password + "', " + (Admin ? 1 : 0) + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get all users from database
    public static void LoadUsers() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            ArrayList<User> users = new ArrayList<>();
            while(rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = Objects.equals(rs.getString("admin"), "1");
                users.add(new User(username, password, admin));
            }
            User.Users = users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LoadItems() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM items");
            List<Item> items = new ArrayList<>();
            List<Film> films = new ArrayList<>();
            List<Book> books = new ArrayList<>();
            List<AudioBook> audioBooks = new ArrayList<>();
            List<BrailleBook> brailleBooks = new ArrayList<>();
            while(rs.next()){
                int ID = rs.getInt("id");
                double day_price = rs.getDouble("day_price");
                String description = rs.getString("description");
                boolean loaned = rs.getInt("loaned") == 1;
                String name = rs.getString("name");
                double overdue_price = rs.getDouble("overdue_price");
                String filename = rs.getString("filename");
                items.add(new Item(day_price, description, ID, loaned, name, overdue_price, filename));
                //check which of the tables contains this ID,
                //then add it to the corresponding list
                ResultSet rs2 = conn.createStatement().executeQuery("SELECT * FROM films WHERE id = " + ID);
                if(rs2.next()){
                    String director = rs2.getString("director");
                    String format = rs2.getString("format");
                    int duration = rs2.getInt("duration");
                    String resolution = rs2.getString("resolution");
                    films.add(new Film(day_price, description, ID, loaned, name, overdue_price,filename, director,
                            duration, format, resolution));
                }
                rs2 = conn.createStatement().executeQuery("SELECT * FROM books WHERE id = " + ID);
                if(rs2.next()){
                    String author = rs2.getString("author");
                    String genre = rs2.getString("genre");
                    String ISBN = rs2.getString("ISBN");
                    String language = rs2.getString("language");
                    String publisher = rs2.getString("publisher");
                    int year = rs2.getInt("year");
                    int pages = rs2.getInt("pages");
                    String type = rs2.getString("type");
                    if(Objects.equals(type, "book")){
                        books.add(new Book(day_price, description, ID, loaned, name, overdue_price,filename, author,
                                genre, ISBN, language, publisher, year, pages));
                    }
                    else if(Objects.equals(type, "audiobook")){
                        String duration = rs2.getString("duration");
                        String narrator = rs2.getString("narrator");
                        String format = rs2.getString("format");
                        audioBooks.add(new AudioBook(day_price, description, ID, loaned, name, overdue_price,filename,
                                author, genre, ISBN, language, publisher, year, duration, narrator, format));
                    }
                    else if(Objects.equals(type, "braillebook")){
                        String transcriber = rs2.getString("transcriber");
                        brailleBooks.add(new BrailleBook(day_price, description, ID, loaned, name, overdue_price,
                                filename, author, genre, ISBN, language, publisher, year, pages, transcriber));
                    }
                }
            }
            Item.Items = items.toArray(new Item[0]);
            Film.Films = films.toArray(new Film[0]);
            Book.Books = books.toArray(new Book[0]);
            AudioBook.AudioBooks = audioBooks.toArray(new AudioBook[0]);
            BrailleBook.BrailleBooks = brailleBooks.toArray(new BrailleBook[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Items loaded - " + Item.Items.length + " items, " + Film.Films.length + " films, " +
                Book.Books.length + " books, " + AudioBook.AudioBooks.length + " audiobooks, " +
                BrailleBook.BrailleBooks.length + " braille books");
    }

    public static User GetUser(String user) {
        for(User u : User.Users){
            if(u.Username.equals(user)){
                return u;
            }
        }
        return null;
    }


    public static ResultSet Query(String Query){
        try {
            return conn.createStatement().executeQuery(Query);
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public static int GetNewItemID(){
        //get the smallest unused ID number from items table
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(id) FROM items");
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT MAX(id) FROM items");
            //check if equal
            if(rs.getInt("COUNT(id)") == rs2.getInt("MAX(id)")){
                System.out.println("New item ID: " + (rs.getInt("COUNT(id)") + 1));
                return rs.getInt("COUNT(id)") + 1;
            }
            else{
                rs = conn.createStatement().executeQuery("SELECT id FROM items");
                int i = 1;
                while(rs.next()){
                    if(rs.getInt("id") != i){
                        System.out.println("New item ID: " + i);
                        return i;
                    }
                    i++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static void InsertItem(Item item, Type type){
        //item fields' id, day_price, description, loaned, name, overdue_price, filename
        int ID = item.ID;
        double day_price = item.DayPrice;
        String description = item.Description;
        int loaned = item.Loaned ? 1 : 0;
        String name = item.Name;
        double overdue_price = item.OverduePrice;
        String filename = item.Filename;
        //insert item into table
        try {
            conn.createStatement().execute("INSERT INTO items (id, day_price, description, loaned, name" +
                    ", overdue_price, filename)" +
                    " VALUES (" + ID + ", " + day_price + ", '" + description + "', " + loaned + ", '" + name
                    + "', " + overdue_price + ", '" + filename + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Item inserted into master table ID: " + ID + " Name: " + name);

        if(type == Film.class){
            InsertFilm((Film) item);
        }else if(type == AudioBook.class) {
            InsertAudio((AudioBook) item);
        }else if (type == BrailleBook.class) {
            InsertBraille((BrailleBook) item);
        }else if (type == Book.class) {
            InsertBook((Book) item);
        }
    }

    private static void InsertBraille(BrailleBook item) {
        int ID = item.ID;
        String author = item.author;
        String genre = item.genre;
        String isbn = item.ISBN;
        String language = item.language;
        int pages = item.Pages;
        String publisher = item.publisher;
        int year = item.year;
        String type = "braillebook";
        String transcriber = item.Transcriber;

        try {
            conn.createStatement().execute("INSERT INTO books (id, author, genre, isbn, language, pages, publisher," +
                    " year, type, transcriber)" +
                    " VALUES (" + ID + ", '" + author + "', '" + genre + "', '" + isbn + "', '" + language + "', "
                    + pages + ", '" + publisher + "', " + year + ", '" + type + "', '" + transcriber + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void InsertAudio(AudioBook item) {
        int ID = item.ID;
        String author = item.author;
        String genre = item.genre;
        String isbn = item.ISBN;
        String language = item.language;
        int pages = item.Pages;
        String publisher = item.publisher;
        int year = item.year;
        String type = "audiobook";
        String duration = item.duration;
        String narrator = item.narrator;
        String format= item.format;

        try {
            conn.createStatement().execute("INSERT INTO books (id, author, genre, isbn, language, pages, publisher," +
                    " year, type, duration, narrator, format)" +
                    " VALUES (" + ID + ", '" + author + "', '" + genre + "', '" + isbn + "', '" + language + "', "
                    + pages + ", '" + publisher + "', " + year + ", '" + type + "', '" + duration + "', '" + narrator +
                    "', '" + format + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void InsertBook(Book item) {
        int ID = item.ID;
        String author = item.author;
        String genre = item.genre;
        String isbn = item.ISBN;
        String language = item.language;
        int pages = item.Pages;
        String publisher = item.publisher;
        int year = item.year;

        try {
            conn.createStatement().execute("INSERT INTO books (id, author, genre, isbn, language, pages, publisher, year)" +
                    " VALUES (" + ID + ", '" + author + "', '" + genre + "', '" + isbn + "', '" + language + "', " + pages + ", '" + publisher + "', " + year + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void InsertFilm(Film item) {
        int ID = item.ID;
        String director = item.Director;
        int duration = item.Duration;
        String format = item.Format;
        String resolution = item.Resolution;

        try {
            conn.createStatement().execute("INSERT INTO films (id, director, duration, format, resolution)" +
                    " VALUES (" + ID + ", '" + director + "', " + duration + ", '" + format + "', '" + resolution + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static <T extends Item> Item GetItemByID(int ID){
        for(Film f : Film.Films){
            if(f.ID == ID){
                return (T) f;
            }
        }
        for(AudioBook a : AudioBook.AudioBooks){
            if(a.ID == ID){
                return (T) a;
            }
        }
        for(BrailleBook b : BrailleBook.BrailleBooks){
            if(b.ID == ID){
                return (T) b;
            }
        }
        for(Book b : Book.Books){
            if(b.ID == ID){
                return (T) b;
            }
        }
        return null;
    }

    static Type GetTypeByID(int ID){
        for(Film f : Film.Films){
            if(f.ID == ID){
                return Film.class;
            }
        }
        for(AudioBook a : AudioBook.AudioBooks){
            if(a.ID == ID){
                return AudioBook.class;
            }
        }
        for(BrailleBook b : BrailleBook.BrailleBooks){
            if(b.ID == ID){
                return BrailleBook.class;
            }
        }
        for(Book b : Book.Books){
            if(b.ID == ID){
                return Book.class;
            }
        }
        return null;
    }

    public static void DeleteUser(String username) {
        try {
            conn.createStatement().execute("DELETE FROM users WHERE username = '" + username + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
