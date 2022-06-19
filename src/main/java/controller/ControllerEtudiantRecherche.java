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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Etudiant;
import modele.Groupe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEtudiantRecherche implements Initializable {


    @FXML
    private TableColumn<Etudiant, String> nomCol, prenomCol, dateCol, descCol, groupeCol, groupeTDCol;
    @FXML
    private TableColumn<Etudiant, Integer> ageCol;
    @FXML
    private TableColumn<Etudiant, Boolean> redCol, demCol;
    @FXML
    private TableView<Etudiant> tableViewSecretaire;
    @FXML
    private TextField searchEtu;
    @FXML
    private ComboBox comboBoxGroupeTP;


    private ObservableList<Groupe> listChoixGroupeTP = FXCollections.observableArrayList();
    private ObservableList<Etudiant> obListEtudiant = FXCollections.observableArrayList(EtudiantDAO.getListeEtudiant());
    private ObservableList<Etudiant> ObListEtudiantRecherche;


    private final String VALEUR_DEFAUT_PROMPT_COMBO_BOX = "Choisir un groupe";
    private final String VALEUR_DEFAUT_PROMPT_TEXT_FIELD = "Nom d'un étudiant";

    @FXML
    void shutdown(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("connexion.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void goback(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("EtudiantHome.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @FXML
    void trombinoscope(ActionEvent event) throws IOException {
        FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("RechercheTrombinoscope.fxml"));
        Scene sceneCalc = new Scene(fxmlCalcLoader.load());
        Main.stage.setScene(sceneCalc);
    }

    @Override /* Initialisation de la page */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComboBoxGroupeTP();
        comboBoxGroupeTP.setPromptText(VALEUR_DEFAUT_PROMPT_COMBO_BOX); //init prompt-text
        searchEtu.setPromptText(VALEUR_DEFAUT_PROMPT_TEXT_FIELD); //init prompt-text
        displayTableView(obListEtudiant); //affiche la liste des étudiant d'un tableView

        if (ControllerHomePage.getPersonnel() != null) {
            // --------------
        }
        else {
            comboBoxGroupeTP.getSelectionModel().select(ControllerHomePage.getEtudiant().getIdGroupeTP());
            onSearch(new ActionEvent());
        }

    }

    public void displayTableView(ObservableList<Etudiant> list){ // Méthode permettant d'afficher le tableau
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        redCol.setCellValueFactory(new PropertyValueFactory<>("red"));
        demCol.setCellValueFactory(new PropertyValueFactory<>("dem"));
        groupeCol.setCellValueFactory(new PropertyValueFactory<>("idGroupeTP"));
        groupeTDCol.setCellValueFactory(new PropertyValueFactory<>("idGroupeTD"));
        tableViewSecretaire.setItems(list);
    }

    public void onSearch(ActionEvent actionEvent) {
        ObListEtudiantRecherche = FXCollections.observableArrayList(EtudiantDAO.getListeEtudiant()); //Copie de la list des étudiant dans cette list

        for(Etudiant curEtu: obListEtudiant){
            if(!(curEtu.getNom().toLowerCase().contains(searchEtu.getText().toLowerCase()))){ //On supprime tout ceux qui ne respectent pas la condtions il restera alros que les bons résultats
                ObListEtudiantRecherche.remove(curEtu);
            }
            if(!(comboBoxGroupeTP.getValue()==null)){ //Si la comboBox n'a pas été selectioner
                if(!(curEtu.getIdGroupeTD().equals(comboBoxGroupeTP.getValue()))){
                    ObListEtudiantRecherche.remove(curEtu);
                }
            }
        }
        if(ObListEtudiantRecherche.isEmpty()){ //Si la la list est vide --> on cherche pas un TD mais un TP
            ObListEtudiantRecherche = FXCollections.observableArrayList(EtudiantDAO.getListeEtudiant()); // On remet tout dans la liste
            for(Etudiant curEtu: obListEtudiant){ //Pour chaque étudiant
                if(!(curEtu.getNom().toLowerCase().contains(searchEtu.getText().toLowerCase()))){ //Pour la recherche par nom
                    ObListEtudiantRecherche.remove(curEtu);
                }
                if (!(curEtu.getIdGroupeTP().equals(comboBoxGroupeTP.getValue()))){ //Pour la recherche de groupe TP
                    ObListEtudiantRecherche.remove(curEtu);
                }
            }

        }

        displayTableView(ObListEtudiantRecherche); //affiche le tableau avec les critères
        ObListEtudiantRecherche.removeAll(); //Supprimer toute la list pour la prochaine recherche
        comboBoxGroupeTP.valueProperty().set(null); // Pour déselectionner la comboBox
        searchEtu.setText(""); // Pour "reset" le textField (pour qu'elle revienne comme au début)
    }

    public void loadComboBoxGroupeTP(){ //Charge les groupes dans la comboBox
        for(Groupe grp: Groupe.getListeGroupe()){
            listChoixGroupeTP.add(grp);
        }
        comboBoxGroupeTP.getItems().addAll(listChoixGroupeTP);
    }

    public void onReset(ActionEvent actionEvent) { //Permet de supprimer tout les critère et affiche tout les étudiant (sans critères)
        displayTableView(obListEtudiant);
    }



}
