package controller;

import dao.PersonnelDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import modele.Personnel;

import java.io.IOException;
import java.util.List;

public class ControllerSecretaireHomePage {

    private List<Personnel> listePersonnel;

    public ControllerSecretaireHomePage() {
        listePersonnel = PersonnelDAO.getListePersonnel(); //On recupere la liste des personnels
    }

    /* <> LINK ENTRE LES PAGES */
    @FXML
    void associerEtudiant(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireAssociationEtudiantGroupe.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void modificationEtudiant(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireModificationEtudiant.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void visualisationEtudiant(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireRecherche.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void modificationGroupe(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireModificationGroupe.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void shutdown(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("Connexion.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

}
