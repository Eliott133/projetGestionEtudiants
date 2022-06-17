module com.example.saegestionetudianttheoeliottlouison {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens application to javafx.fxml;
    exports application;
}