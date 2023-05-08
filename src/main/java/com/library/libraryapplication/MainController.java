package com.library.libraryapplication;

import com.library.libraryapplication.Items.*;
import com.library.libraryapplication.Users.Administrator;
import com.library.libraryapplication.Users.UnprivellagedUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class MainController {
    private final Stage stage;

    @FXML
    private TableView dataTable;
    @FXML
    private Button Manage;

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
        row.put("OverduePrice", rowData.OverduePrice);
        row.put("DayPrice", rowData.DayPrice);
        row.put("Description", rowData.Description);
        dataTable.getItems().add(row);
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
        var row = (java.util.Map<String, Object>) dataTable.getSelectionModel().getSelectedItem();
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
        var row = (java.util.Map<String, Object>) dataTable.getSelectionModel().getSelectedItem();
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
    private void infoBtn(){

    }
    @FXML
    private void printFileBtn(){}
    @FXML
    private void reloadBtn(){}
    @FXML
    private void removeItemBtn(){}
    @FXML
    private void addItemBtn(){}
    @FXML
    private void manageBtn(){}

}
