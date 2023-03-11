module com.library.libraryapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.library.libraryapplication to javafx.fxml;
    exports com.library.libraryapplication;
}