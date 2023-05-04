module com.library.libraryapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.library.libraryapplication to javafx.fxml;
    exports com.library.libraryapplication;
}