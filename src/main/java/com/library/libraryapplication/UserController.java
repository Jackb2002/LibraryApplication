package com.library.libraryapplication;

import com.library.libraryapplication.Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    Stage stage;

    @FXML
    private TextField userTxt;
    @FXML
    private PasswordField passTxt;
    @FXML
    private CheckBox adminBox;
    @FXML
    private ListView listBox;

    public UserController(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setMaxHeight(405);
        stage.setMaxWidth(600);
        stage.setMinHeight(405);
        stage.setMinWidth(600);
    }

    @FXML
    public void initialize() {
        listBox.getItems().clear();
        for (User user : User.Users) {
            if(User.Current == user){
                continue;
            }
            listBox.getItems().add(user.Username);
        }
    }

    @FXML
    private void makeBtn(){
        var selected = listBox.getSelectionModel().getSelectedItems();
        var username = selected.get(0).toString();
        User.Users.stream().findFirst().filter(user -> user.Username.equals(username))
                .ifPresent(user -> user.Admin = true); // predicate to find user make admin
    }
    @FXML
    private void takeBtn(){
        var selected = listBox.getSelectionModel().getSelectedItems();
        var username = selected.get(0).toString();
        User.Users.stream().findFirst().filter(user -> user.Username.equals(username))
                .ifPresent(user -> user.Admin = false); // predicate to find user remove admin
    }
    @FXML
    private void addBtn(){
        if(userTxt.getText().equals("") || passTxt.getText().equals("")){
            return;
        }
        User newUser = new User(userTxt.getText(), passTxt.getText(), adminBox.isSelected());
        User.Users.add(newUser);
        Database.InsertUser(newUser);
        initialize();
    }
    @FXML
    private void removeBtn(){
        var selected = listBox.getSelectionModel().getSelectedItems();
        var username = selected.get(0).toString();
        User.Users.removeIf(user -> user.Username.equals(username));
        Database.DeleteUser(username);
        initialize();
    }

    @FXML
    private void closeBtn(){
        try {
            LibraryApplication.OpenMainWindow(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.close();
    }
}
