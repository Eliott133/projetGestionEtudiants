package application;

import DAO.EtudiantDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;

public class ControllerEtudiantInfos {

    private List<Etudiant> listeEtudiant;
    private Etudiant etudiant = ControllerHomePage.getEtudiant();

    @FXML
    private Label age, dateNaissance, dem, desc, groupeTP, mail, nom, prenom, red, groupeTD;

    @FXML
    private ImageView photoEtu;


    public ControllerEtudiantInfos() {
        listeEtudiant = EtudiantDAO.getListeEtudiant(); //On recupere la liste des etudiants
    }

    @FXML
    public void initialize() {
        mail.setText("Identifiant : " + etudiant.getNum());
        prenom.setText("Prenom : " + etudiant.getPrenom());
        nom.setText("Nom : " + etudiant.getNom());
        age.setText("Age : " + String.valueOf(etudiant.getAge()));
        dateNaissance.setText("Date de naissance : " + etudiant.getDate());
        desc.setText("Description : " + etudiant.getDesc());
        red.setText("Redoublant : " + String.valueOf(etudiant.isRed()));
        dem.setText("Demissionaire : " + String.valueOf(etudiant.isDem()));
        groupeTP.setText("Groupe TP : " + etudiant.getIdGroupeTP().getNom());
        photoEtu.setImage(new Image(String.valueOf(ControllerHomePage.class.getResource("/photo/" + etudiant.getPhoto() + ".jpg"))));
        groupeTD.setText("Groupe TD" + etudiant.getIdGroupeTD().getNom());
    }

    /* <> LINK ENTRE LES PAGES */
    @FXML
    void shutdown(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("Connexion.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void goback(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("etudiantHome.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    public void onAction(ActionEvent actionEvent) {
    }
}
