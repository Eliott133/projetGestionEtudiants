package application;

import dao.EtudiantDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import modele.Etudiant;
import modele.Groupe;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ControllerSecretaireModificationEtudiant {

    @FXML
    private DatePicker dateEtu;

    @FXML
    private ComboBox<Groupe> groupeEtu;

    @FXML
    private TextField nomEtu, descEtu, numEtu, numEtuSupp, prenomEtu, ageEtu;

    @FXML
    private Button photoEtu, supprimerButton, ajouterButton;

    @FXML
    private RadioButton radioNon, radioOui;

    @FXML
    private Label labelPhoto, infoSuppr;

    private String photo;

    @FXML
    void onButton(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files","*jpg")
        );
        File f = fc.showOpenDialog(null); //Pour ouvrir l'explorateur de fichier windows

        if (f != null){ //peut choisir un seul fichier
            labelPhoto.setText(" " + f.getName()); //Affiche le nom du fichier selectionner
        }
        photo = f.getAbsolutePath(); //Retourne le path
    }


    @FXML
    void ajouter(ActionEvent event) {

        if(numEtu.getText() != null && prenomEtu.getText() != null && nomEtu.getText() != null && ageEtu.getText() != null && dateEtu.getValue() != null && labelPhoto != null && groupeEtu.getValue() != null && radioOui.isSelected()) {
            //On regarde que tous les criteres sont remplis avant d'ajouter un etudiant

            int age = Integer.parseInt(String.valueOf(ageEtu.getText()));

            Etudiant etudiant = new Etudiant(numEtu.getText(), nomEtu.getText(), prenomEtu.getText(), age, dateEtu.getValue().toString(), descEtu.getText(), "defaultPhoto", true,false, groupeEtu.getValue(), (Groupe) groupeEtu.getValue().getGroupeParent());
            //On cree l'etudiant

            EtudiantDAO.addEtudiant(etudiant);
            //On sauvegarde l'etudiant dans la base de donnees

        } else if(numEtu.getText() != null && prenomEtu.getText() != null && nomEtu.getText() != null && ageEtu.getText() != null && dateEtu.getValue() != null && labelPhoto != null && groupeEtu.getValue() != null && radioNon.isSelected()){
            //On regarde que tous les criteres sont remplis avant d'ajouter un etudiant

            int age = Integer.parseInt(String.valueOf(ageEtu.getText()));

            Etudiant etudiant = new Etudiant(numEtu.getText(), nomEtu.getText(), prenomEtu.getText(), age, dateEtu.getValue().toString(), descEtu.getText(), "defaultPhoto", false,false, groupeEtu.getValue(), (Groupe) groupeEtu.getValue().getGroupeParent());
            //On cree l'etudiant

            EtudiantDAO.addEtudiant(etudiant);
            //On sauvegarde l'etudiant dans la base de donnees
        }

        numEtu.setText(null);
        prenomEtu.setText(null);
        nomEtu.setText(null);
        ageEtu.setText("");
        dateEtu.setValue(null);
        descEtu.setText(null);
        labelPhoto.setText("Photo");
        groupeEtu.setValue(new Groupe(null));
        radioNon.setToggleGroup(null);
        radioOui.setToggleGroup(null);
        //On remet les textes à vide (question ergonomique)

    }

    @FXML
    void supprimer(ActionEvent event) {
        Iterator<Etudiant> it = EtudiantDAO.getListeEtudiant().iterator();
        while (it.hasNext()) {
            Etudiant unEtu = it.next();
            if(numEtuSupp.getText().equals(unEtu.getNum())) {
                it.remove();
                EtudiantDAO.deleteEtudiant(unEtu);
            }
        }
        numEtuSupp.setText("");
    }

    /* <> LINK ENTRE LES PAGES */
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

    @FXML
    public void initialize(){

        infoSuppr.setVisible(false);

        List<Groupe> li = Groupe.getListeGroupeTP();
        ObservableList<Groupe> oli = FXCollections.observableList(li);
        groupeEtu.setItems(oli);
        //On met les donnees dans la combobox

        ToggleGroup group = new ToggleGroup();
        radioNon.setToggleGroup(group);
        radioOui.setToggleGroup(group);
        //On cree le toggle group pour oui et non

        ageEtu.textProperty().addListener(new ChangeListener<String>() {
            //Fonction qui permet de ne pouvoir taper que des chiffres dans le textfield (notamment pour l'age)
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    ageEtu.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
