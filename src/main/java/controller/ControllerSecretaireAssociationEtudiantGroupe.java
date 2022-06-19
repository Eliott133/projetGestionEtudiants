package controller;

import dao.EtudiantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modele.Etudiant;
import modele.Groupe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSecretaireAssociationEtudiantGroupe implements Initializable {

    @FXML
    private ObservableList listChoixGroupeTP = FXCollections.observableArrayList();

    @FXML
    private ComboBox comboBoxGroupeTP;

    @FXML
    private TextField searchEtuById;

    @FXML
    void shutdown(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("connexion.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void goback(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireHome.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    public void loadComboBoxGroupeTP(){ //Charge les groupes dans la comboBox
        for(Groupe grp: Groupe.getListeGroupeTP()){
            listChoixGroupeTP.add(grp);
        }
        comboBoxGroupeTP.getItems().addAll(listChoixGroupeTP);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComboBoxGroupeTP();
    }

    private Groupe nouveauGroupeTD, nouveauGroupeTP;

    public void onAjout(ActionEvent actionEvent) {
        for (Etudiant curEtu: EtudiantDAO.getListeEtudiant()){
            if(searchEtuById.getText().equals(curEtu.getNum())){
                curEtu.setIdGroupeTP((Groupe) comboBoxGroupeTP.getValue());
                curEtu.setIdGroupeTD((Groupe) curEtu.getIdGroupeTP().getGroupeParent());
                EtudiantDAO.updateEtudiant(curEtu, curEtu.getIdGroupeTD(), curEtu.getIdGroupeTP());
            }
        }

    }
}