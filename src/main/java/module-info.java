module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.controller;
    opens com.example.controller to javafx.fxml;
    exports com.example.view;
    opens com.example.view to javafx.fxml;


    opens com.example.classes to javafx.base;

}