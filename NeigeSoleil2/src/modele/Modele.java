package modele;

import bdd.BDD;
import java.sql.*;
import java.util.ArrayList;

public class Modele {

    // ----------- AUTH ADMIN -----------
    public static boolean connexionAdmin(String email, String mdp) {
        String sql = "SELECT * FROM user WHERE email=? AND mot_de_passe=? AND type='admin'";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ------------- CRUD USER -------------------
    public static ArrayList<String[]> getListeUsers() {
        ArrayList<String[]> liste = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = BDD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String[] user = {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("type")
                };
                liste.add(user);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public static boolean ajouterUser(String nom, String email, String mdp, String type) {
        String sql = "INSERT INTO user(nom, email, mot_de_passe, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, email);
            ps.setString(3, mdp);
            ps.setString(4, type);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean supprimerUser(int id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean majUser(int id, String nom, String email, String mdp, String type) {
        String sql = "UPDATE user SET nom=?, email=?, mot_de_passe=?, type=? WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, email);
            ps.setString(3, mdp);
            ps.setString(4, type);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ------------- CRUD LOGEMENT -------------------
    public static ArrayList<String[]> getListeLogements() {
        ArrayList<String[]> liste = new ArrayList<>();
        String sql = "SELECT * FROM logements";
        try (Connection conn = BDD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String[] logement = {
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getInt("proprietaire_id")),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("adresse"),
                    String.valueOf(rs.getDouble("prix_par_nuit")),
                    String.valueOf(rs.getBoolean("disponible")),
                    rs.getString("code_postal"),
                    rs.getString("nb_chambres"),
                    rs.getString("type_logement")
                };
                liste.add(logement);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public static boolean ajouterLogement(int proprietaire_id, String titre, String description, String adresse, double prix_par_nuit, boolean disponible, String code_postal, int nb_chambres, String type_logement) {
        String sql = "INSERT INTO logements (proprietaire_id, titre, description, adresse, prix_par_nuit, disponible, code_postal, nb_chambres, type_logement) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proprietaire_id);
            ps.setString(2, titre);
            ps.setString(3, description);
            ps.setString(4, adresse);
            ps.setDouble(5, prix_par_nuit);
            ps.setBoolean(6, disponible);
            ps.setString(7, code_postal);
            ps.setInt(8, nb_chambres);
            ps.setString(9, type_logement);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean supprimerLogement(int id) {
        String sql = "DELETE FROM logements WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean majLogement(int id, int proprietaire_id, String titre, String description, String adresse, double prix_par_nuit, boolean disponible, String code_postal, int nb_chambres, String type_logement) {
        String sql = "UPDATE logements SET proprietaire_id=?, titre=?, description=?, adresse=?, prix_par_nuit=?, disponible=?, code_postal=?, nb_chambres=?, type_logement=? WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proprietaire_id);
            ps.setString(2, titre);
            ps.setString(3, description);
            ps.setString(4, adresse);
            ps.setDouble(5, prix_par_nuit);
            ps.setBoolean(6, disponible);
            ps.setString(7, code_postal);
            ps.setInt(8, nb_chambres);
            ps.setString(9, type_logement);
            ps.setInt(10, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ------------- CRUD RESERVATION -------------------
    public static ArrayList<String[]> getListeReservations() {
        ArrayList<String[]> liste = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Connection conn = BDD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String[] reservation = {
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getInt("client_id")),
                    rs.getString("nom_client"),
                    rs.getString("prenom"),
                    rs.getString("email_client"),
                    rs.getString("telephone"),
                    String.valueOf(rs.getInt("logement_id")),
                    rs.getString("date_debut"),
                    rs.getString("date_fin"),
                    String.valueOf(rs.getInt("nb_personnes")),
                    rs.getString("commentaire"),
                    rs.getString("statut"),
                    rs.getString("date_reservation")
                };
                liste.add(reservation);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public static boolean ajouterReservation(int client_id, String nom_client, String prenom, String email_client, String telephone, int logement_id, String date_debut, String date_fin, int nb_personnes, String commentaire, String statut) {
        String sql = "INSERT INTO reservations (client_id, nom_client, prenom, email_client, telephone, logement_id, date_debut, date_fin, nb_personnes, commentaire, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, client_id);
            ps.setString(2, nom_client);
            ps.setString(3, prenom);
            ps.setString(4, email_client);
            ps.setString(5, telephone);
            ps.setInt(6, logement_id);
            ps.setString(7, date_debut);
            ps.setString(8, date_fin);
            ps.setInt(9, nb_personnes);
            ps.setString(10, commentaire);
            ps.setString(11, statut);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean supprimerReservation(int id) {
        String sql = "DELETE FROM reservations WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean majReservation(int id, int client_id, String nom_client, String prenom, String email_client, String telephone, int logement_id, String date_debut, String date_fin, int nb_personnes, String commentaire, String statut) {
        String sql = "UPDATE reservations SET client_id=?, nom_client=?, prenom=?, email_client=?, telephone=?, logement_id=?, date_debut=?, date_fin=?, nb_personnes=?, commentaire=?, statut=? WHERE id=?";
        try (Connection conn = BDD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, client_id);
            ps.setString(2, nom_client);
            ps.setString(3, prenom);
            ps.setString(4, email_client);
            ps.setString(5, telephone);
            ps.setInt(6, logement_id);
            ps.setString(7, date_debut);
            ps.setString(8, date_fin);
            ps.setInt(9, nb_personnes);
            ps.setString(10, commentaire);
            ps.setString(11, statut);
            ps.setInt(12, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
