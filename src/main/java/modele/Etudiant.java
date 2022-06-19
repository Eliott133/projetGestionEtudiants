package modele;

public class Etudiant {

    private String num;
    private String nom;
    private String prenom;
    private int age;
    private String date;
    private String desc;
    private String photo;
    private boolean red;
    private boolean dem;
    private Groupe idGroupeTP;
    private Groupe idGroupeTD;

    public Etudiant(String num, String nom, String prenom, int age, String date, String desc, String photo,
                    boolean red, boolean dem, Groupe idGroupeTP, Groupe idGroupeTD) {
        super();
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.date = date;
        this.desc = desc;
        this.photo = photo;
        this.red = red;
        this.dem = dem;
        this.idGroupeTP = idGroupeTP;
        this.idGroupeTD = idGroupeTD;
    }

    public String getNum() {
        return num;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isRed() {
        return red;
    }

    public boolean isDem() {
        return dem;
    }

    public Groupe getIdGroupeTP() {
        return idGroupeTP;
    }

    public Groupe getIdGroupeTD() {
        return idGroupeTD;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "num='" + num + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", date='" + date + '\'' +
                ", desc='" + desc + '\'' +
                ", photo='" + photo + '\'' +
                ", red=" + red +
                ", dem=" + dem +
                ", idGroupeTP=" + idGroupeTP +
                '}';
    }

    public void setIdGroupeTP(Groupe newGroupeTP){
        this.idGroupeTP = newGroupeTP;
    }

    public void setIdGroupeTD(Groupe newGroupeTD){
        this.idGroupeTD = newGroupeTD;
    }
}