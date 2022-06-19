package dao;

import modele.Etudiant;
import modele.Groupe;
import util.ConnectionBDD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class EtudiantDAO implements util.DAO {

    private static List<Etudiant> listeEtudiant = new ArrayList<>();

    public EtudiantDAO() {
        Connection con = ConnectionBDD.getConnection();
        String query = "SELECT Num_etu, Nom_etu, Prenom_etu, Age_etu, Date_etu, Desc_etu, Photo_etu, Red_etu, Dem_etu, Id_groupe_TP, Id_groupe_TD FROM ETUDIANT;";
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData resultMeta = rs.getMetaData(); //Stocke les resultats de la commande SQL
            while(rs.next()){

                    //================CONVERTIR 0 ET 1 EN BOOLEAN================
                    Boolean red = false;
                    Boolean dem = false;
                    if (rs.getObject(8).toString().equals("0")) {
                        red = true;
                    }
                    if (rs.getObject(9).toString().equals("0")) {
                        dem = true;
                    }
                    //================CONVERTIR 0 ET 1 EN BOOLEAN================

                    Groupe groupeTD = searchGroupe(rs.getObject(11).toString());
                    Groupe groupeTP = searchGroupe(rs.getObject(10).toString(), groupeTD);

                    Etudiant newEtudiant = new Etudiant(rs.getObject(1).toString(), rs.getObject(2).toString(), rs.getObject(3).toString(), Integer.valueOf(rs.getObject(4).toString()), rs.getObject(5).toString(), rs.getObject(6).toString(), rs.getObject(7).toString(), red, dem, groupeTP, groupeTD);
                    //On cree l'etudiant

                    groupeTD.addEtudiant(newEtudiant);
                    groupeTP.addEtudiant(newEtudiant);
                    //On rajoute au groupe TP et TD les etudiants

                    listeEtudiant.add(newEtudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEtudiant(Etudiant etudiant) {
        Connection con = ConnectionBDD.getConnection();

        EtudiantDAO.getListeEtudiant().remove(etudiant);
        String query = "DELETE FROM ETUDIANT WHERE Num_etu='" + etudiant.getNum() + "';";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query); //On execute la commande SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Groupe searchGroupe(String nomGroupe, Groupe groupeParent) {
        //A partir du nom du groupe et du parent du groupe, on regarde si le groupe existe
        List<Groupe> listeGroupe = Groupe.getListeGroupe();
        for (int i = 0; i < listeGroupe.size(); i++) {
            if (listeGroupe.get(i).getNom().equals(nomGroupe)) {
                return listeGroupe.get(i); //Si oui, on retourne le groupe deja existant
            }
        }
        Groupe nouveauGroupe = new Groupe(nomGroupe, groupeParent);
        return nouveauGroupe;
        //Si non, on cree le nouveau groupe
    }

    private Groupe searchGroupe(String nomGroupe) {
        //A partir du nom du groupe on regarde si le groupe existe
        List<Groupe> listeGroupe = Groupe.getListeGroupe();
        for (int i = 0; i < listeGroupe.size(); i++) {
            if (listeGroupe.get(i).getNom().equals(nomGroupe)) {
                return listeGroupe.get(i); //Si oui, on retourne le groupe deja existant
            }
        }
        Groupe nouveauGroupe = new Groupe(nomGroupe);
        return nouveauGroupe;
        //Si non, on cree le nouveau groupe
    }

    public static List<Etudiant> getListeEtudiant() {
        return listeEtudiant;
    }

    public static void addEtudiant(Etudiant personne) {
        Connection con = ConnectionBDD.getConnection();

        listeEtudiant.add(personne);
        Groupe groupeTDEtudiant = personne.getIdGroupeTD();
        Groupe groupeTPEtudiant = personne.getIdGroupeTP();

        groupeTDEtudiant.addEtudiant(personne);
        groupeTPEtudiant.addEtudiant(personne);

        saveEtudiant(personne);
    }

    private static void saveEtudiant(Etudiant etudiant) {
        Connection con = ConnectionBDD.getConnection();

        //================CONVERTIR 0 ET 1 EN BOOLEAN================
        int red = 1;
        int dem = 1;
        if (etudiant.isRed()) {
            red = 0;
        }
        if (etudiant.isDem()) {
            dem = 0;
        }
        //================CONVERTIR 0 ET 1 EN BOOLEAN================

        String query = "INSERT INTO ETUDIANT VALUES('" + etudiant.getNum() + "', '" + etudiant.getNom() + "', '" + etudiant.getPrenom() + "', " + etudiant.getAge() + ", " + etudiant.getDate() + ", '" + etudiant.getDesc() + "', '" + etudiant.getPhoto() + "', " + red + ", " + dem + ", '" + etudiant.getIdGroupeTD() + "', '" + etudiant.getIdGroupeTP() + "', 1);";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query); //On execute la commande SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEtudiant(Etudiant etu, Groupe nouveauGroupeTD, Groupe nouveauGroupeTP){
        Connection con = ConnectionBDD.getConnection();

        String query = "UPDATE ETUDIANT SET Id_groupe_TD='" + nouveauGroupeTD + "', Id_groupe_TP='" + nouveauGroupeTP +"' WHERE Num_etu='" + etu.getNum() + "';";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
