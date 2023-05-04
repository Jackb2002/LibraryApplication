package com.library.libraryapplication;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;

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
    }

    //logoutBtn
    @FXML
    private void logoutBtn() {
        stage.hide();
        System.exit(0);
    }

}
