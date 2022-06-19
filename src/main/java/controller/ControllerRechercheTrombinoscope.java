package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.Etudiant;
import modele.Groupe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerRechercheTrombinoscope {

    private List<ImageView> listeImageView = new ArrayList<>();
    private Integer page = 0;
    List<Etudiant> listeEtudiant = new ArrayList<>();

    @FXML
    private Pane p = new Pane();

    @FXML
    private ComboBox<Groupe> nomGroupe;

    @FXML
    private Label nombrePage;

    @FXML
    private Button pageMoins, pagePlus;

    @FXML
    void pageMoins(ActionEvent event) {
        //Fonction pour voir si il faut afficher le bouton pour changer de page (moins)
        if ((page-1)*15 >= 0) {
            pagePlus.setVisible(true); //Si on prend une page en moins, alors le bouton plus est forcement visible
            page--;
            nombrePage.setText(page+1 + " / " + (Integer.valueOf((listeEtudiant.size()-1) / 15)+1));
            if (page == 0) {
                pageMoins.setVisible(false);
            }
            changeGroupeTrombinoscope();
        }
    }

    @FXML
    void pagePlus(ActionEvent event) {
        //Fonction pour voir si il faut afficher le bouton pour changer de page (plus)
        if ((page+1)*15 < listeEtudiant.size()) {
            page++;
            pageMoins.setVisible(true); //Si on prend une page en plus, alors le bouton moins est forcement visible
            nombrePage.setText(page+1 + " / " + (Integer.valueOf((listeEtudiant.size()-1) / 15)+1));
            if (!((page+1)*15 < listeEtudiant.size())) {
                pagePlus.setVisible(false);
            }
            changeGroupeTrombinoscope();
        }
    }

    @FXML
    void changeTrombinoscope(ActionEvent event) {
        //Fonction refresh des images des etudiants
        Groupe groupe = nomGroupe.getValue();
        listeEtudiant = groupe.getListeEtudiantGroupe();
        changePage();
        changeGroupeTrombinoscope();
    }

    private void changeGroupeTrombinoscope() {
        for (int j = 0; j < listeImageView.size(); j++) {
            p.getChildren().remove(listeImageView.get(j)); //On enleve toutes les anciennes images
        }
        listeImageView = new ArrayList<>();
        for (int i = page*15; i < (page+1)*15; i++) {
            if (i < listeEtudiant.size()) {
                ImageView imageView = new ImageView(); //On cree l'image

                imageView.setImage(new Image(String.valueOf(ControllerHomePage.class.getResource("/photo/" + listeEtudiant.get(i).getPhoto() + ".jpg")))); //On met la bonne image

                imageView.setFitWidth(100);
                imageView.setFitHeight(100); //On met la bonne taille

                imageView.setX(228 + (i % 5) * 120); //On met la bonne position
                imageView.setY(150 + Integer.valueOf((i - (page * 15)) / 5) * 120);

                imageView.setId("ImageView" + i); //On met un id

                p.getChildren().add(imageView); //On rajoute l'image a SceneBuilder

                listeImageView.add(imageView);
            }
        }
    }

    private void changePage() {
        //Fonction qui met a jour le label qui affiche le nombre de pages
        page = 0;
        pageMoins.setVisible(false);
        nombrePage.setText(page+1 + " / " + (Integer.valueOf((listeEtudiant.size()-1) / 15)+1));
        if (!((page+1)*15 < listeEtudiant.size())) {
            pagePlus.setVisible(false);
        }
        else {
            pagePlus.setVisible(true);
        }
    }

    private List<ImageView> listeImages = new ArrayList<>();

    @FXML
    public void initialize() {

        pageMoins.setVisible(false);

        List<Groupe> li = Groupe.getListeGroupe();
        ObservableList<Groupe> oli = FXCollections.observableList(li);
        nomGroupe.setItems(oli);
        //On met dans la combobox

        if (ControllerHomePage.getEtudiant() != null) {
            nomGroupe.getSelectionModel().select(ControllerHomePage.getEtudiant().getIdGroupeTP()); //On met la valeur de combobox par defaut qui est le groupe TP de l'etudiant
            Groupe groupe = nomGroupe.getValue();
            listeEtudiant = groupe.getListeEtudiantGroupe();
            changeGroupeTrombinoscope();
            nombrePage.setText(page+1 + " / " + (Integer.valueOf((listeEtudiant.size()-1) / 15)+1));
        }

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
        FXMLLoader fxmlCalcLoader = null;
        if (ControllerHomePage.getPersonnel() != null) {
            if (ControllerHomePage.getPersonnel().getRole().contains("Secretaire")) {
                //Pourquoi on test le role secretaire ?
                //Si un personnel a le double role enseignant/secretaire, vu que le role secretaire permet de faire
                //plus de choses, on le met en secretaire au lieu de professeur
                fxmlCalcLoader = new FXMLLoader(Main.class.getResource("SecretaireRecherche.fxml"));
            }
            else {
                fxmlCalcLoader = new FXMLLoader(Main.class.getResource("EnseignantRecherche.fxml"));
            }

        }
        else {
            fxmlCalcLoader = new FXMLLoader(Main.class.getResource("etudiantHome.fxml"));
        }

        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

}
