module com.example.saegestionetudianttheoeliottlouison {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens controller to javafx.fxml;
    exports controller;
    exports modele;
    opens modele to javafx.fxml;
}