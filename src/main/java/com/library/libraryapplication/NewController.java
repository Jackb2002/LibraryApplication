package com.library.libraryapplication;

import com.library.libraryapplication.Items.AudioBook;
import com.library.libraryapplication.Items.Book;
import com.library.libraryapplication.Items.BrailleBook;
import com.library.libraryapplication.Items.Film;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class NewController {
    private final Stage stage;
    @FXML
    private ComboBox combo;
    @FXML
    private TextArea name;
    @FXML
    private TextArea dayPrice;
    @FXML
    private TextArea oPrice;
    @FXML
    private TextArea desc;
    @FXML
    private TextArea director;
    @FXML
    private TextArea duration;
    @FXML
    private TextArea format;
    @FXML
    private TextArea reso;
    @FXML
    private TextArea pages;
    @FXML
    private TextArea author;
    @FXML
    private TextArea genre;
    @FXML
    private TextArea isbn;
    @FXML
    private TextArea publisher;
    @FXML
    private TextArea language;
    @FXML
    private TextArea year;
    @FXML
    private TextArea transcriber;
    @FXML
    private TextArea audioduration;
    @FXML
    private TextArea narrator;
    @FXML
    private TextArea audioformat;

    public NewController(Stage stage){
        this.stage = stage;
        stage.setResizable(false);
        stage.setMaxHeight(400);
        stage.setMaxWidth(395);
        stage.setMinHeight(400);
        stage.setMinWidth(395);
    }

    @FXML
    public void initialize() {
        combo.getItems().removeAll(combo.getItems());
        combo.getItems().addAll("Book", "Film", "Audiobook", "Braille");
        combo.getSelectionModel().select("Book");
    }

    @FXML
    private void addBtn(){
        var d_Price = dayPrice.getText();
        var o_Price = oPrice.getText();
        //check valid doubles
        try{
            if(Double.parseDouble(d_Price) > 0 || Double.parseDouble(o_Price) > 0){
                return;
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid");
            alert.setHeaderText("Invalid");
            alert.setContentText("Invalid price data entered");
            alert.showAndWait();
            return;
        }

        var currentCombo = combo.getSelectionModel().getSelectedItem().toString();
        switch(currentCombo){
            case("Book"):
                Book newBook = new Book(
                        Double.parseDouble(dayPrice.getText()),
                        desc.getText(),
                        Database.GetNewItemID(),
                        false,
                        name.getText(),
                        Double.parseDouble(oPrice.getText()),
                        "book.jpg",
                        author.getText(),
                        genre.getText(),
                        isbn.getText(),
                        language.getText(),
                        publisher.getText(),
                        Integer.parseInt(year.getText()),
                        Integer.parseInt(pages.getText()));

                Database.InsertItem(newBook, Book.class);
                break;
            case("Film"):
                Film newFilm = new Film(
                        Double.parseDouble(dayPrice.getText()),
                        desc.getText(),
                        Database.GetNewItemID(),
                        false,
                        name.getText(),
                        Double.parseDouble(oPrice.getText()),
                        "film.jpg",
                        director.getText(),
                        Integer.parseInt(duration.getText()),
                        format.getText(),
                        reso.getText());

                Database.InsertItem(newFilm, Film.class);
                break;
            case("Audiobook"):
                AudioBook newAudioBook = new AudioBook(
                        Double.parseDouble(dayPrice.getText()),
                        desc.getText(),
                        Database.GetNewItemID(),
                        false,
                        name.getText(),
                        Double.parseDouble(oPrice.getText()),
                        "audio.jpg",
                        author.getText(),
                        genre.getText(),
                        isbn.getText(),
                        language.getText(),
                        publisher.getText(),
                        Integer.parseInt(year.getText()),
                        audioduration.getText(),
                        narrator.getText(),
                        audioformat.getText());

                Database.InsertItem(newAudioBook, AudioBook.class);
                break;
            case("Braille"):
                BrailleBook newBrailleBook = new BrailleBook(
                        Double.parseDouble(dayPrice.getText()),
                        desc.getText(),
                        Database.GetNewItemID(),
                        false,
                        name.getText(),
                        Double.parseDouble(oPrice.getText()),
                        "braille.jpg",
                        author.getText(),
                        genre.getText(),
                        isbn.getText(),
                        language.getText(),
                        publisher.getText(),
                        Integer.parseInt(year.getText()),
                        Integer.parseInt(pages.getText()),
                        transcriber.getText());

                Database.InsertItem(newBrailleBook, BrailleBook.class);
                break;
        }
        System.out.println("Added new record");
        exit();

    }
    @FXML
    private void cancelBtn(){
        System.out.println("Cancel button pressed");
        exit();
    }

    private void exit() {
        Stage stage = new Stage();
        try {
            LibraryApplication.OpenMainWindow(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.stage.close();
    }

    @FXML
    private void comboAction(){
        String selected = combo.getSelectionModel().getSelectedItem().toString();
        if(selected.equals("Book")){
            pages.disableProperty().set(false);
            author.disableProperty().set(false);
            genre.disableProperty().set(false);
            isbn.disableProperty().set(false);
            publisher.disableProperty().set(false);
            language.disableProperty().set(false);
            year.disableProperty().set(false);
            transcriber.disableProperty().set(true);
            audioduration.disableProperty().set(true);
            narrator.disableProperty().set(true);
            audioformat.disableProperty().set(true);
            director.disableProperty().set(true);
            duration.disableProperty().set(true);
            format.disableProperty().set(true);
            reso.disableProperty().set(true);
        }
        else if(selected.equals("Film")){
            director.disableProperty().set(false);
            duration.disableProperty().set(false);
            format.disableProperty().set(false);
            reso.disableProperty().set(false);
            pages.disableProperty().set(true);
            author.disableProperty().set(true);
            genre.disableProperty().set(true);
            isbn.disableProperty().set(true);
            publisher.disableProperty().set(true);
            language.disableProperty().set(true);
            year.disableProperty().set(true);
            transcriber.disableProperty().set(true);
            audioduration.disableProperty().set(true);
            narrator.disableProperty().set(true);
            audioformat.disableProperty().set(true);
        }
        else if(selected.equals("Audiobook")){
            pages.disableProperty().set(false);
            author.disableProperty().set(false);
            genre.disableProperty().set(false);
            isbn.disableProperty().set(false);
            publisher.disableProperty().set(false);
            language.disableProperty().set(false);
            year.disableProperty().set(false);
            audioduration.disableProperty().set(false);
            narrator.disableProperty().set(false);
            audioformat.disableProperty().set(false);
            transcriber.disableProperty().set(true);
            director.disableProperty().set(true);
            duration.disableProperty().set(true);
            format.disableProperty().set(true);
            reso.disableProperty().set(true);
        }
        else if(selected.equals("Braille")){
            pages.disableProperty().set(false);
            author.disableProperty().set(false);
            genre.disableProperty().set(false);
            isbn.disableProperty().set(false);
            publisher.disableProperty().set(false);
            language.disableProperty().set(false);
            year.disableProperty().set(false);
            transcriber.disableProperty().set(false);
            audioduration.disableProperty().set(true);
            narrator.disableProperty().set(true);
            audioformat.disableProperty().set(true);
            director.disableProperty().set(true);
            duration.disableProperty().set(true);
            format.disableProperty().set(true);
            reso.disableProperty().set(true);
        }
    }
}
