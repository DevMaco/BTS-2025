package modele;

import java.sql.*;
import java.util.ArrayList;

public class Modele {
    private static Connection connexion;

    public static void connecter(String url, String user, String mdp) {
        try {
            connexion = DriverManager.getConnection(url, user, mdp);
            System.out.println("Connexion réussie.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnexion() {
        return connexion;
    }

    // TODO: Ajouter les méthodes de gestion des logements et réservations ici
}

    // ------------------- LOGEMENTS -------------------
    public static List<Logement> getAllLogements() {
        List<Logement> liste = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM logement");
            while (rs.next()) {
                liste.add(new Logement(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getDouble("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static void ajouterLogement(Logement l) {
        try {
            PreparedStatement ps = connexion.prepareStatement("INSERT INTO logement (nom, adresse, prix) VALUES (?, ?, ?)");
            ps.setString(1, l.getNom());
            ps.setString(2, l.getAdresse());
            ps.setDouble(3, l.getPrix());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------- RESERVATIONS -------------------
    public static List<Reservation> getAllReservations() {
        List<Reservation> liste = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservation");
            while (rs.next()) {
                liste.add(new Reservation(
                    rs.getInt("id"),
                    rs.getInt("idLogement"),
                    rs.getString("nomClient"),
                    rs.getDate("dateDebut").toLocalDate(),
                    rs.getDate("dateFin").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static void ajouterReservation(Reservation r) {
        try {
            PreparedStatement ps = connexion.prepareStatement("INSERT INTO reservation (idLogement, nomClient, dateDebut, dateFin) VALUES (?, ?, ?, ?)");
            ps.setInt(1, r.getIdLogement());
            ps.setString(2, r.getNomClient());
            ps.setDate(3, Date.valueOf(r.getDateDebut()));
            ps.setDate(4, Date.valueOf(r.getDateFin()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifierLogement(Logement l) {
        try {
            PreparedStatement ps = connexion.prepareStatement("UPDATE logement SET nom = ?, adresse = ?, prix = ? WHERE id = ?");
            ps.setString(1, l.getNom());
            ps.setString(2, l.getAdresse());
            ps.setDouble(3, l.getPrix());
            ps.setInt(4, l.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerLogement(int id) {
        try {
            PreparedStatement ps = connexion.prepareStatement("DELETE FROM logement WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    
    }
    
