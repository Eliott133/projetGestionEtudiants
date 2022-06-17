package application;

import DAO.EtudiantDAO;
import DAO.GroupeDAO;
import DAO.PersonnelDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConnectionBDD;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new ConnectionBDD();
        //On se connecte a la base de donnes

        GroupeDAO groupeDAO = new GroupeDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        //On initialise les groupes, personnels et etudiants

        launch(args);
    }

}
