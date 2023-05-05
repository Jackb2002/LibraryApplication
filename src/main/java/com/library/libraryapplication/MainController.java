package com.library.libraryapplication;

import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.util.List;

public class MainController {
    private final Stage stage;

    @FXML
    private javafx.scene.control.TableView dataTable;


    public MainController(Stage stage){
        this.stage = stage;
        stage.setWidth(1200);
        stage.setHeight(600);
        System.out.println("MainController created");
        System.out.println("Loading data from database...");
        System.out.println("Data loaded");


        stage.setOnCloseRequest(event -> {
            logoutBtn();
        });

    }



    //logoutBtn
    @FXML
    private void logoutBtn() {
        System.out.println("Dumping database contents...");
        stage.hide();
        DatabaseSerialiser.SaveUsers(List.of(Administrator.Users),
                List.of(UnprivellagedUser.Users));
        System.exit(0);
    }

}
