package modele;

import java.util.ArrayList;
import java.util.List;

public class Groupe {

    private static List<Groupe> listeGroupe = new ArrayList<>();
    private static List<Groupe> listeGroupeTP = new ArrayList<>();
    private static List<Groupe> listeGroupeTD = new ArrayList<>();
    private List<Etudiant> listeEtudiantGroupe = new ArrayList<>();
    private String nom;
    private Groupe groupeParent; //Exemple : TP11B a comme groupe parent TD11

    public Object getGroupeParent() {
        return groupeParent;
    }

    public static List<Groupe> getListeGroupeTP() {
        return listeGroupeTP;
    }

    public static List<Groupe> getListeGroupeTD() {
        return listeGroupeTD;
    }

    public Groupe(String nom) {
        //C'est un groupe TD puisqu'il n'y a pas de groupe parent
        listeGroupe.add(this);
        this.nom = nom;
        listeGroupeTD.add(this);
    }

    public Groupe(String nom, Groupe parent) {
        //C'est un groupe TP puisqu'il n'y a pas de groupe parent
        listeGroupe.add(this);
        this.groupeParent = parent;
        this.nom = nom;
        listeGroupeTP.add(this);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setGroupeParent(Groupe groupeParent) {
        this.groupeParent = groupeParent;
    }

    public static List<Groupe> getListeGroupe() {
        return listeGroupe;
    }

    public List<Etudiant> getListeEtudiantGroupe() {
        return listeEtudiantGroupe;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    public void addEtudiant(Etudiant etu) {
        listeEtudiantGroupe.add(etu);
    }
}
