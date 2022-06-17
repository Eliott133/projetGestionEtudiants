package application;

import java.util.ArrayList;
import java.util.List;

public class Personnel {

    private String harpege;
    private String nom;
    private String prenom;
    private List<String> role = new ArrayList<>();

    public Personnel(String harpege, String nom, String prenom, String role) {
        super();
        this.harpege = harpege;
        this.nom = nom;
        this.prenom = prenom;
        this.role.add(role);
    }

    public String getHarpege() {
        return harpege;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        String strRole = "";
        for (int i = 0; i < role.size(); i++) {
            strRole += role.get(i);
        }
        return "Personnel{" +
                "harpege='" + harpege + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role=" + strRole +
                '}';
    }

    public List<String> getRole() {
        return role;
    }

    public void addRole(String role) {
        this.role.add(role);
    }

}
