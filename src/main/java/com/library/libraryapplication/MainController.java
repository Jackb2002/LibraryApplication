package com.library.libraryapplication;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.sql.*;

public class MainController {
    private Stage stage;

    @FXML
    private javafx.scene.control.TableView dataTable;


    public MainController(Stage stage) {
        this.stage = stage;
        stage.setWidth(1200);
        stage.setHeight(600);
        System.out.println("MainController created");
        System.out.println("Loading data from database...");
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM items");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //logoutBtn
    @FXML
    private void logoutPress() {
        stage.hide();
        System.exit(0);
    }

}
