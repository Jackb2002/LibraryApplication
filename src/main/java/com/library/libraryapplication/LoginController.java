package com.library.libraryapplication;

import com.library.libraryapplication.Users.User;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private final Stage stage;
    @FXML
    private javafx.scene.control.TextField passTxt;
    @FXML
    private javafx.scene.control.TextField userTxt;

    public LoginController(Stage stage) {
        this.stage = stage;
        System.out.println("LoginController created");
    }
    @FXML
    private void exitPress() {
        System.exit(0);
    }
    @FXML
    private void loginPress() throws IOException {
        String user = userTxt.getText();
        String pass = passTxt.getText();
        if (Database.Login(user, pass)) {
            System.out.println("Login successful");
            User.Current = Database.GetUser(user);
            stage.hide();
            Stage newStage = new Stage();
            LibraryApplication.OpenMainWindow(newStage);
        }
        else {
            System.out.println("Login failed");
        }
    }
}
