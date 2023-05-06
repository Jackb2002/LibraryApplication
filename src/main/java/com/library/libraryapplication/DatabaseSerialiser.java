package com.library.libraryapplication;

import com.library.libraryapplication.Database;
import com.library.libraryapplication.Items.AudioBook;
import com.library.libraryapplication.Items.Book;
import com.library.libraryapplication.Items.BrailleBook;
import com.library.libraryapplication.Items.Film;
import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import com.library.libraryapplication.Users.User;
import javafx.scene.chart.PieChart;

import java.util.Arrays;
import java.util.List;

public class DatabaseSerialiser {
    public static void SaveUsers(List<User> admins, List<User> users) {
        System.out.println("Saving users to database");
        System.out.println("Users: " + users.size());

        //Clear previous contents of database
        Database.ClearTable("users");

        //Insert new users
        for (User user : users) {
            if (user instanceof UnprivellagedUser) {
                Database.InsertUser(user, UnprivellagedUser.class);
            } else if (user instanceof Administrator) {
                Database.InsertUser(user, Administrator.class);
            }
        }
    }

    public static void LoadUsers(){
        System.out.println("Loading users from database");
        Database.LoadUsers();
        System.out.println("Loaded users");
    }

    public static void LoadItems(){
        System.out.println("Loading items from database");
        Database.LoadItems();
        System.out.println("Loaded items");
    }

    public static void SaveItems(List<Film> films, List<Book> books, List<AudioBook> audioBooks,
                                 List<BrailleBook> brailleBooks){
        Database.ClearTable("items");
        Database.ClearTable("books");
        Database.ClearTable("films");

        for (Film film: films) {
            Database.InsertItem(film, Film.class);
        }
        for (AudioBook audioBook: audioBooks) {
            Database.InsertItem(audioBook, AudioBook.class);
        }
        for (BrailleBook brailleBook: brailleBooks) {
            Database.InsertItem(brailleBook, BrailleBook.class);
        }
        for (Book book: books) {
            Database.InsertItem(book, Book.class);
        }
    }


}
