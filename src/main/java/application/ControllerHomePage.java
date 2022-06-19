package application;

import dao.EtudiantDAO;
import dao.PersonnelDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Etudiant;
import modele.Personnel;

import java.io.IOException;
import java.util.List;

public class ControllerHomePage {

    @FXML
    private Label erreur;

    @FXML
    private TextField id;

    private static Etudiant etudiant; //On stocke soit l'etudiant, soit le personnel qui est acutellement utilise
    private static Personnel personnel; //Cela nous permettre d'acceder a certaines conditions

    private List<Etudiant> listeEtudiant;
    private List<Personnel> listePersonnel;


    public static Etudiant getEtudiant() {
        return etudiant;
    }

    public static Personnel getPersonnel() {
        return personnel;
    }

    public ControllerHomePage() {
        listeEtudiant = EtudiantDAO.getListeEtudiant();
        listePersonnel = PersonnelDAO.getListePersonnel();
        //On recupere dans la base de donnees les etudiants
    }

    @FXML
    public void connexion() throws IOException {
        etudiant = null;
        personnel = null;
        //Fonction pour connecter un etudiant ou un personnel
        for (int i = 0; i < listeEtudiant.size(); i++) {
            if (id.getText().equals(listeEtudiant.get(i).getNum())) {
                //Si l'id correspond
                erreur.setVisible(false);
                etudiant = listeEtudiant.get(i);

                FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("etudiantHome.fxml"));
                Scene sceneCalc = new Scene(fxmlCalcLoader.load());
                Main.stage.setScene(sceneCalc);
            }
        }
        for (int i = 0; i < listePersonnel.size(); i++) {
            if (id.getText().equals(listePersonnel.get(i).getHarpege())) {
                erreur.setVisible(false);
                personnel = listePersonnel.get(i);
                if (listePersonnel.get(i).getRole().get(0).equals("Secretaire")) { //On regarde si le personnel a pour role "secretaire"
                    //Pourquoi on test le role secretaire ?
                    //Si un personnel a le double role enseignant/secretaire, vu que le role secretaire permet de faire
                    //plus de choses, on le met en secretaire au lieu de professeur

                    FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("secretaireHome.fxml"));
                    Scene sceneCalc = new Scene(fxmlCalcLoader.load());
                    Main.stage.setScene(sceneCalc);
                }
                else if (listePersonnel.get(i).getRole().get(0).equals("Enseignant")) { //On regarde si le personnel a pour role "Enseignant"
                    FXMLLoader fxmlCalcLoader = new FXMLLoader(Main.class.getResource("EnseignantRecherche.fxml"));
                    Scene sceneCalc = new Scene(fxmlCalcLoader.load());
                    Main.stage.setScene(sceneCalc);
                }
            }
        }
        erreur.setVisible(true);
    }
}