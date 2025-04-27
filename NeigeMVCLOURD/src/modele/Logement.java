package modele;

public class Logement {
    private int id;
    private String nom;
    private String adresse;
    private double prix;

    public Logement(int id, String nom, String adresse, double prix) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.prix = prix;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public double getPrix() { return prix; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public String toString() {
        return nom + " - " + adresse + " (" + prix + "€/nuit)";
    }
}
