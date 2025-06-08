package controleur;

public class Controleur_user {
    private int id;
    private String nom;
    private String email;
    private String mot_de_passe;
    private String type;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMot_de_passe() { return mot_de_passe; }
    public void setMot_de_passe(String mot_de_passe) { this.mot_de_passe = mot_de_passe; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
