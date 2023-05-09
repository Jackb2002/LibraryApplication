package com.library.libraryapplication;

import com.library.libraryapplication.Items.*;
import com.library.libraryapplication.Users.User;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            if(User.Current.Admin){
                Manage.disableProperty().set(false);
            }
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
        DatabaseSerialiser.SaveUsers(User.Users);
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

        //count copies
        Item item = Database.GetItemByID(ID);
        String name = item.Name;
        //see how many books have the same name
        int count = 0;
        for(Item i : Item.Items){
            if(i.Name.equals(name)){
                count++;
            }
        }
        info += "\n\nThere are " + count + " copies of this item in the library.";

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
    private void manageBtn(){
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("users.fxml"));
        fxmlLoader.setController(new UserController(stage));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 240);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Manage Users");
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void imageBtn(){
        var row = getCurrentRow();
        if(row == null){
            System.out.println("No row selected");
            return;
        }
        int id = (int) row.get("ID");
        Item item = Database.GetItemByID(id);
        var file = item.Filename;
        if(file == null){
            System.out.println("No image found");
            return;
        }

        //image popup
        Image image = new Image(file);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        Stage stage = new Stage();
        stage.setTitle("Image");
        stage.setScene(new Scene(new StackPane(imageView)));
        stage.show();
        //wait 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> stage.close() );
    }
    @FXML
    private void reportBtn(){
        System.out.println("Generating Report");
        String report = "";
        report += "Total Items: " + Item.Items.length + "\n";
        report += "Total Books: " + Book.Books.length + "\n";
        report += "Total Audio Books: " + AudioBook.AudioBooks.length + "\n";
        report += "Total Braille Books: " + BrailleBook.BrailleBooks.length + "\n";
        report += "Total Films: " + Film.Films.length + "\n";
        report += "Total Users: " + User.Users.size() + "\n";
        report += "Total Admins: " + User.Users.stream().filter(user -> user.Admin).count() + "\n";
        report += "Time Left For Loans:" + "\n";
        double loan_profit = 0;
        try {
            var rs = Database.Query("SELECT * FROM loan_times");
            while(rs.next()) {
                int ID = rs.getInt("id");
                Item item = Database.GetItemByID(ID);
                loan_profit += item.DayPrice;
                String loaned_at = rs.getString("loan_time");
                String due_at = rs.getString("due_time");
                report += "ID: " + ID + " Loaned At: " + loaned_at + " Due At: " + due_at + "\n";
            }
        }catch(SQLException e){
            System.out.println("Error getting loan times");
        }
        report += "Total Profit From Loans: £" + loan_profit + "\n";
        outputConsole.setText(report);
    }
}
