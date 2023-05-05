package com.library.libraryapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("login.fxml"));
        fxmlLoader.setController(new LoginController(stage));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library login");
        stage.setScene(scene);
        stage.show();
    }

    static void OpenMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("main.fxml"));
        fxmlLoader.setController(new MainController(stage));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Database.Init();
        Database.LoadUsers();
        launch();
    }
}