package controleur;

public class Controleur_logement {
    private int id;
    private int proprietaire_id;
    private String titre;
    private String description;
    private String adresse;
    private double prix_par_nuit;
    private boolean disponible;
    private String code_postal;
    private int nb_chambres;
    private String type_logement;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProprietaire_id() { return proprietaire_id; }
    public void setProprietaire_id(int proprietaire_id) { this.proprietaire_id = proprietaire_id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public double getPrix_par_nuit() { return prix_par_nuit; }
    public void setPrix_par_nuit(double prix_par_nuit) { this.prix_par_nuit = prix_par_nuit; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getCode_postal() { return code_postal; }
    public void setCode_postal(String code_postal) { this.code_postal = code_postal; }

    public int getNb_chambres() { return nb_chambres; }
    public void setNb_chambres(int nb_chambres) { this.nb_chambres = nb_chambres; }

    public String getType_logement() { return type_logement; }
    public void setType_logement(String type_logement) { this.type_logement = type_logement; }
}
