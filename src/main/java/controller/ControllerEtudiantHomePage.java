package controller;

import dao.EtudiantDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import modele.Etudiant;

import java.io.IOException;
import java.util.List;

public class ControllerEtudiantHomePage {

    private List<Etudiant> listeEtudiant;

    public ControllerEtudiantHomePage() {
        listeEtudiant = EtudiantDAO.getListeEtudiant();
    }

    /* <> LINK ENTRE LES PAGES */
    @FXML
    void mesinfos(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("etudiantInformation.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void mapromo(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("etudiantRecherche.fxml"));
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
