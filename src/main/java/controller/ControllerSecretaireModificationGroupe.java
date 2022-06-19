package controller;

import dao.EtudiantDAO;
import dao.GroupeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Groupe;

import java.io.IOException;
import java.util.List;

public class ControllerSecretaireModificationGroupe {

    @FXML
    private ComboBox<Object> groupeParentAjout, groupeParentModification;

    @FXML
    private TextField nomGroupeAjout, nouveauNomGroupe;

    @FXML
    private ComboBox<Groupe> nomGroupeSuppression, groupeModification;

    @FXML
    private Label labelErreur;

    private ObservableList<Object> listDeTD = FXCollections.observableArrayList();
    private ObservableList<Object> listDeTDavecAucun = FXCollections.observableArrayList();

    @FXML
    void modifier(ActionEvent event) {
        labelErreur.setVisible(false);
        if (nouveauNomGroupe.getText() != "" && groupeModification.getValue() != null) {
            //On verifie qu'il y a au moins un nom de groupe a modifier ainsi que son nouveau nom qui contient quelque chose

            Groupe groupeModifie = null;
            String Id_groupe = groupeModification.getValue().getNom();
            Groupe groupeParent = null;

            if (groupeModification.getValue().getGroupeParent() != null) { //Si c'est un TP
                groupeModifie = groupeModification.getValue();
                Id_groupe = nouveauNomGroupe.getText();

                if (groupeParentModification.getValue() != null) {
                    //Si un groupe parent a ete indique et que le groupe modifie est un TP
                    groupeParent = (Groupe) groupeParentModification.getValue();
                }

                else {
                    //Si aucun groupe parent n'a ete indique et que le groupe modifie est un TP
                    groupeParent = (Groupe) groupeModifie.getGroupeParent();
                }

            }

            else {
                //Si le groupe modifié est un TD
                groupeModifie = groupeModification.getValue();
                Id_groupe = nouveauNomGroupe.getText();
            }

            groupeModifie.setGroupeParent(groupeParent);
            GroupeDAO.updateGroupe(groupeModifie, Id_groupe);
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        //Methode pour supprimer un etudiant de la base de donnees
        labelErreur.setVisible(false);

        if (nomGroupeSuppression.getValue() != null) {
            Groupe.getListeGroupe().remove(nomGroupeSuppression.getValue());
            //On supprime de la liste des groupes

            if (nomGroupeSuppression.getValue().getGroupeParent() != null) { //Si c'est un groupe TP
                Groupe.getListeGroupeTP().remove(nomGroupeSuppression.getValue());
                //On supprime de la liste des groupes TP

            }
            else { //Si c'est un groupe TD
                Groupe.getListeGroupeTD().remove(nomGroupeSuppression.getValue());
                //On supprime de la liste des groupes TD

            }
            GroupeDAO.deleteGroupe(nomGroupeSuppression.getValue());
            //On supprime de la base de donnees

            for (int i = 0; i < EtudiantDAO.getListeEtudiant().size(); i++) {
                //On regarde si il y a un etudiant dans le groupe, ce qui provoquera une erreur dans la base de donnees SQL
                if (EtudiantDAO.getListeEtudiant().get(i).getIdGroupeTP().equals(nomGroupeSuppression.getValue()) || EtudiantDAO.getListeEtudiant().get(i).getIdGroupeTD().equals(nomGroupeSuppression.getValue())) {
                    labelErreur.setVisible(true);
                    //On affiche l'erreur
                }
            }
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        labelErreur.setVisible(false);
        if (nomGroupeAjout.getText() != "" && groupeParentAjout.getValue() != null) {
            //Si les criteres sont remplis

            Groupe nouveauGroupe = null;
            if (groupeParentAjout.getValue().equals("aucun")) {
                nouveauGroupe = new Groupe(nomGroupeAjout.getText());
            }
            else {
                nouveauGroupe = new Groupe(nomGroupeAjout.getText(), (Groupe) groupeParentAjout.getValue());
            }

            GroupeDAO.saveGroupe(nouveauGroupe);
            //On sauvegarde dans la base de donnees

            setComboBox();
        }
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
        List<Groupe> li = Groupe.getListeGroupe();
        ObservableList<Groupe> listGroupe = FXCollections.observableArrayList(li);
        groupeModification.setItems(listGroupe);
        //On met les items dans la combobox

        setComboBox();
    }

    private void setComboBox() {
        List<Groupe> li = Groupe.getListeGroupeTD();
        listDeTDavecAucun = FXCollections.observableArrayList(li);
        listDeTDavecAucun.add("aucun");
        listDeTD = FXCollections.observableArrayList(li);
        //On cree deux listes pour ajouter dans les deux combobox

        groupeParentModification.setItems(listDeTD);
        groupeParentAjout.setItems(listDeTDavecAucun);
        //On ajoute les items dans les deux listes

        li = Groupe.getListeGroupe();
        ObservableList<Groupe> listGroupe = FXCollections.observableArrayList(li);
        nomGroupeSuppression.setItems(listGroupe);
        //On ajoute les items dans la liste

    }

}
