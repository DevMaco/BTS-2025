package controleur;

public class Controleur_reservation {
    private int id;
    private int client_id;
    private String nom_client;
    private String prenom;
    private String email_client;
    private String telephone;
    private int logement_id;
    private String date_debut;
    private String date_fin;
    private int nb_personnes;
    private String commentaire;
    private String statut;
    private String date_reservation;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClient_id() { return client_id; }
    public void setClient_id(int client_id) { this.client_id = client_id; }

    public String getNom_client() { return nom_client; }
    public void setNom_client(String nom_client) { this.nom_client = nom_client; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail_client() { return email_client; }
    public void setEmail_client(String email_client) { this.email_client = email_client; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public int getLogement_id() { return logement_id; }
    public void setLogement_id(int logement_id) { this.logement_id = logement_id; }

    public String getDate_debut() { return date_debut; }
    public void setDate_debut(String date_debut) { this.date_debut = date_debut; }

    public String getDate_fin() { return date_fin; }
    public void setDate_fin(String date_fin) { this.date_fin = date_fin; }

    public int getNb_personnes() { return nb_personnes; }
    public void setNb_personnes(int nb_personnes) { this.nb_personnes = nb_personnes; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getDate_reservation() { return date_reservation; }
    public void setDate_reservation(String date_reservation) { this.date_reservation = date_reservation; }
}
