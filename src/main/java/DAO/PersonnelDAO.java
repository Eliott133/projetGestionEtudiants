package DAO;

import application.Personnel;
import util.ConnectionBDD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO implements util.DAO {

    private static List<Personnel> listePersonnel = new ArrayList<Personnel>();

    public PersonnelDAO() {
        Connection con = ConnectionBDD.getConnection();
        String query = "SELECT PERSONNEL.Harpege, Nom_per, Prenom_per, ROLES.Nom_role FROM PERSONNEL INNER JOIN ASSIGNATION ON PERSONNEL.Harpege = ASSIGNATION.Harpege INNER JOIN ROLES ON ASSIGNATION.Id_role = ROLES.Id_role ORDER BY PERSONNEL.Harpege;";
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query); //On execute la commande SQL
            ResultSetMetaData resultMeta = rs.getMetaData(); //Stocke les resultats de la commande SQL

            while(rs.next()) {
                boolean personnelFait = false;
                for (int j = 0; j < listePersonnel.size(); j++) {
                    //But de la boucle: checker si le personnel existe deja dans la base de donnees
                    //(ça voudra dire qu'il a deux roles)
                    if (listePersonnel.get(j).getHarpege().equals(rs.getObject(1).toString())) {
                        //Un personnel a deux roles
                        listePersonnel.get(j).addRole(rs.getObject(4).toString());
                        personnelFait = true;
                    }
                }

                if (!personnelFait) {
                    Personnel newPersonnel = new Personnel(rs.getObject(1).toString(), rs.getObject(2).toString(), rs.getObject(3).toString(), rs.getObject(4).toString());
                    //On cree un nouveau personnel

                    listePersonnel.add(newPersonnel);
                    //On l'ajoute a la liste
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Personnel> getListePersonnel() {
        return listePersonnel;
    }

}