package DAO;

import application.Groupe;
import util.ConnectionBDD;
import java.sql.*;
import java.util.List;

public class GroupeDAO implements util.DAO {

    public GroupeDAO() {
        Connection con = ConnectionBDD.getConnection();

        String query = "SELECT * FROM GROUPE;";
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query); //On execute la commande SQL
            ResultSetMetaData resultMeta = rs.getMetaData(); //Stocke les resultats de la commande SQL
            while(rs.next()){
                if (rs.getObject(2).toString().equals("TD")) { //Si c'est un TD
                    Groupe groupeTD = searchGroupe(rs.getObject(1).toString());
                }
                else { //Si c'est un TP
                    Groupe groupeParent = findParent(rs.getObject(1).toString());
                    Groupe groupeTD = new Groupe(rs.getObject(1).toString(), groupeParent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Groupe findParent(String nomGroupeTP) {
        String nomGroupeTD = nomGroupeTP.substring(0, nomGroupeTP.length()-1);
        //On recupere l'id du groupe TD

        for (int i = 0; i < Groupe.getListeGroupeTD().size(); i++) { //On regarde si il en existe un dans la base de donnees
            if (Groupe.getListeGroupeTD().get(i).getNom().equals(nomGroupeTD)) { //Si oui, on retourne le groupe
                return Groupe.getListeGroupeTD().get(i);
            }
        }
        return new Groupe(nomGroupeTD); //Si non, on en cree un nouveau
    }

    private Groupe searchGroupe(String nomGroupe) {
        //A partir du nom du groupe on regarde si le groupe existe
        List<Groupe> listeGroupe = Groupe.getListeGroupe();
        for (int i = 0; i < listeGroupe.size(); i++) {
            if (listeGroupe.get(i).getNom().equals(nomGroupe)) { //Si oui, on retourne le groupe deja existant
                return listeGroupe.get(i);
            }
        }
        Groupe nouveauGroupe = new Groupe(nomGroupe);
        return nouveauGroupe;
        //Si non, on cree le nouveau groupe
    }

    public static void saveGroupe(Groupe groupe) {
        Connection con = ConnectionBDD.getConnection();

        //================TROUVER LE TYPE DE GROUPE================
        String typeGroupe = "TD";
        if (Groupe.getListeGroupeTP().contains(groupe)) {
            typeGroupe = "TP";
        }
        //================TROUVER LE TYPE DE GROUPE================

        String query = "INSERT INTO GROUPE VALUES('" + groupe.getNom() + "', '" + typeGroupe + "');";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query); //On execute la commande
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGroupe(Groupe groupeModifie, String nouvelle_id) {
        Connection con = ConnectionBDD.getConnection();

        String query = "UPDATE GROUPE SET Id_groupe='" + nouvelle_id + "' WHERE Id_groupe='" + groupeModifie.getNom() + "';";
        groupeModifie.setNom(nouvelle_id);
        //On change le nom dans la liste

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query); //On execute la commande dans la base de donnees
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGroupe(Groupe groupe) {
        Connection con = ConnectionBDD.getConnection();
        String query = "DELETE FROM GROUPE WHERE Id_groupe='" + groupe.getNom() + "';";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query); //On execute la commande dans la base de donnees
        } catch (SQLException e) {

        }
    }
}
