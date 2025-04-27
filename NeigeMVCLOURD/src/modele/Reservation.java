package modele;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int idLogement;
    private String nomClient;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Reservation(int id, int idLogement, String nomClient, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.idLogement = idLogement;
        this.nomClient = nomClient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() { return id; }
    public int getIdLogement() { return idLogement; }
    public String getNomClient() { return nomClient; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }

    public void setId(int id) { this.id = id; }
    public void setIdLogement(int idLogement) { this.idLogement = idLogement; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    @Override
    public String toString() {
        return nomClient + " - " + dateDebut + " au " + dateFin;
    }
}
