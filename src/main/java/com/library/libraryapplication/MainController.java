package com.library.libraryapplication;

import com.library.libraryapplication.Items.*;
import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class MainController {
    private final Stage stage;

    @FXML
    private TableView dataTable;
    @FXML
    private Button Manage;

    @FXML
    private TextArea outputConsole;

    public MainController(Stage stage){
        this.stage = stage;
        stage.setResizable(false);
        stage.setMaxHeight(600);
        stage.setMaxWidth(900);
        stage.setMinHeight(600);
        stage.setMinWidth(900);

        System.out.println("MainController created");
        System.out.println("Loading item data from database...");
        Database.LoadItems();
        System.out.println("Data loaded");

        stage.setOnCloseRequest(event -> logoutBtn());
        stage.setOnShown(event -> {

            dataTable.getItems().clear();
            dataTable.setPlaceholder(new Label("Library Data Loading...."));
            LoadDataIntoTable();
        });
    }

    private void LoadDataIntoTable() {
        //Clear whole data table
        dataTable.getItems().clear();

        //Add columns to table
        dataTable.getColumns().clear();
        var idColumn = new TableColumn("ID");
        dataTable.getColumns().add(idColumn);
        var nameColumn = new TableColumn("Name");
        dataTable.getColumns().add(nameColumn);
        var loaned = new TableColumn("Loaned");
        dataTable.getColumns().add(loaned);
        var overduePrice = new TableColumn("Overdue Price");
        dataTable.getColumns().add(overduePrice);
        var priceColumn = new TableColumn("Price");
        dataTable.getColumns().add(priceColumn);
        var descColumn = new TableColumn("Description");
        dataTable.getColumns().add(descColumn);


        //map columns to fields of Item
        idColumn.setCellValueFactory(new MapValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new MapValueFactory<>("Name"));
        loaned.setCellValueFactory(new MapValueFactory<>("Loaned"));
        overduePrice.setCellValueFactory(new MapValueFactory<>("OverduePrice"));
        priceColumn.setCellValueFactory(new MapValueFactory<>("DayPrice"));
        descColumn.setCellValueFactory(new MapValueFactory<>("Description"));

        //Load data into table
        for (Item item : Item.Items){
            addRowToTable(item);
        }
    }

    private void addRowToTable(Item rowData) {
        var row = new java.util.HashMap<String, Object>();
        row.put("ID", rowData.ID);
        row.put("Name", rowData.Name);
        row.put("Loaned", rowData.Loaned ? "Yes" : "No");
        row.put("OverduePrice", "£" + rowData.OverduePrice + "/day");
        row.put("DayPrice", "£" + rowData.DayPrice);
        row.put("Description", rowData.Description);
        dataTable.getItems().add(row);
    }

    private Map<String, Object> getCurrentRow() {
        var row = (Map<String, Object>) dataTable.getSelectionModel().getSelectedItem();
        return row;
    }

    //logoutBtn
    @FXML
    private void logoutBtn() {
        System.out.println("Dumping database contents...");
        stage.hide();
        DatabaseSerialiser.SaveUsers(List.of(Administrator.Users),
                List.of(UnprivellagedUser.Users));
        DatabaseSerialiser.SaveItems(List.of(Film.Films), List.of(Book.Books),
                List.of(AudioBook.AudioBooks), List.of(BrailleBook.BrailleBooks));
        System.exit(0);
    }

    @FXML
    private void loanBtn() {
        System.out.println("Loan button pressed");
        //get current row from dataTable
        Map<String, Object> row = getCurrentRow();
        if (row == null) {
            System.out.println("No row selected");
            return;
        }

        int ID = (int) row.get("ID");
        if(Database.GetItemByID(ID) == null){
            System.out.println("Item not found");
            return;
        }
        Item item = Database.GetItemByID(ID);

        //check if item is already loaned
        if (Objects.requireNonNull(item).Loaned) {
            System.out.println("Item already loaned");
            return;
        }
        item.Loaned = true;
        int index = dataTable.getItems().indexOf(row);
        row.put("Loaned", "Yes");
        dataTable.getItems().set(index, row);
        item.Loan();
    }

    @FXML
    private void returnBtn(){
        System.out.println("Return button pressed");
        //get current row from dataTable
        Map<String, Object> row = getCurrentRow();
        if (row == null) {
            System.out.println("No row selected");
            return;
        }

        int ID = (int) row.get("ID");
        if(Database.GetItemByID(ID) == null){
            System.out.println("Item not found");
            return;
        }
        Item item = Database.GetItemByID(ID);

        //check if item is already loaned
        if (!Objects.requireNonNull(item).Loaned) {
            System.out.println("Item already returned");
            return;
        }
        item.Loaned = false;

        int index = dataTable.getItems().indexOf(row);
        row.put("Loaned", "No");
        dataTable.getItems().set(index, row);
        item.Return();
    }

    @FXML
    private String infoBtn(){
        Map<String, Object> row = getCurrentRow();
        if (row == null) {
            System.out.println("No row selected");
            return "";
        }

        int ID = (int) row.get("ID");
        Type t = Database.GetTypeByID(ID);
        if(t == null){
            System.out.println("Item not found");
            return "";
        }
        String info = "";
        if(t == Film.class){
            Film f = (Film) Database.GetItemByID(ID);
            info = f.GetInfoText();
        }
        else if(t == AudioBook.class) {
            AudioBook a = (AudioBook) Database.GetItemByID(ID);
            info = a.GetInfoText();
        }
        else if(t == BrailleBook.class) {
            BrailleBook b = (BrailleBook) Database.GetItemByID(ID);
            info = b.GetInfoText();
        }
        else if(t == Book.class) {
            Book b = (Book) Database.GetItemByID(ID);
            info = b.GetInfoText();
        }
        else{
            System.out.println("Item not found");
        }

        outputConsole.setText(info);
        return info;
    }
    @FXML
    private void printFileBtn(){
        String info = infoBtn();
        outputConsole.setText("");
        if(info.equals("")){
            return;
        }
        try {
            File file = new File("info.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(info);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File Saved");
        alert.setHeaderText("File Saved");
        alert.setContentText("File Saved to info.txt");
        alert.showAndWait();
    }
    @FXML
    private void reloadBtn(){LoadDataIntoTable();}
    @FXML
    private void removeItemBtn(){
        var row = getCurrentRow();

        if (row == null) {
            System.out.println("No row selected");
            return;
        }

        int ID = (int) row.get("ID");
        if(Database.GetItemByID(ID) == null){
            System.out.println("Item not found");
            return;
        }

        DatabaseSerialiser.removeItem(ID);

        DatabaseSerialiser.LoadItems();
        reloadBtn();
    }
    @FXML
    private void addItemBtn(){
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("New.fxml"));
        fxmlLoader.setController(new NewController(stage));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("New Record");
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void manageBtn(){}

}
