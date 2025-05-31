package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Modele {
    private static BDD uneBDD = new BDD("root", "", "localhost:3306", "neige"); // adapte si besoin

    // Connexion utilisateur
    public static HashMap<String, String> connexionUtilisateur(String email, String mdp) {
        HashMap<String, String> user = null;
        String requete = "SELECT * FROM user WHERE email = ? AND mot_de_passe = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setString(1, email);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new HashMap<>();
                user.put("id", rs.getString("id"));
                user.put("nom", rs.getString("nom"));
                user.put("email", rs.getString("email"));
                user.put("type", rs.getString("type")); // client/proprietaire
            }
            ps.close();
            uneBDD.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur connexionUtilisateur : " + exp.getMessage());
        }
        return user;
    }

    // Inscription utilisateur
    public static boolean inscrireUtilisateur(HashMap<String, String> utilisateur) {
        boolean ok = false;
        try {
            uneBDD.seConnecter();
            // Vérifie si l'email existe déjà
            String check = "SELECT * FROM user WHERE email = ?";
            PreparedStatement psCheck = uneBDD.getMaConnexion().prepareStatement(check);
            psCheck.setString(1, utilisateur.get("email"));
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                psCheck.close();
                uneBDD.seDeConnecter();
                return false; // email déjà utilisé
            }
            psCheck.close();

            String req = "INSERT INTO user (nom, email, mot_de_passe, type) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(req);
            ps.setString(1, utilisateur.get("nom"));
            ps.setString(2, utilisateur.get("email"));
            ps.setString(3, utilisateur.get("mdp"));
            ps.setString(4, utilisateur.get("type")); // client ou proprietaire
            ps.executeUpdate();
            ps.close();
            uneBDD.seDeConnecter();
            ok = true;
        } catch (SQLException exp) {
            System.out.println("Erreur inscription utilisateur : " + exp.getMessage());
        }
        return ok;
    }

    // Liste des logements
    public static ArrayList<HashMap<String, String>> getLogements() {
        ArrayList<HashMap<String, String>> liste = new ArrayList<>();
        String requete = "SELECT * FROM logements";
        try {
            uneBDD.seConnecter();
            Statement st = uneBDD.getMaConnexion().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                HashMap<String, String> log = new HashMap<>();
                log.put("id", rs.getString("id"));
                log.put("proprietaire_id", rs.getString("proprietaire_id"));
                log.put("titre", rs.getString("titre"));
                log.put("description", rs.getString("description"));
                log.put("adresse", rs.getString("adresse"));
                log.put("prix_par_nuit", rs.getString("prix_par_nuit"));
                log.put("disponible", rs.getString("disponible"));
                log.put("code_postal", rs.getString("code_postal"));
                log.put("nb_chambres", rs.getString("nb_chambres"));
                log.put("type_logement", rs.getString("type_logement"));
                liste.add(log);
            }
            st.close();
            uneBDD.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur getLogements : " + exp.getMessage());
        }
        return liste;
    }

    // Ajout d'un logement par le propriétaire
    public static boolean ajouterLogement(
        int proprietaireId,
        String titre,
        String description,
        String adresse,
        double prixParNuit,
        String codePostal,
        int nbChambres,
        String typeLogement
    ) {
        boolean ok = false;
        String requete = "INSERT INTO logements (proprietaire_id, titre, description, adresse, prix_par_nuit, disponible, code_postal, nb_chambres, type_logement) "
                       + "VALUES (?, ?, ?, ?, ?, 1, ?, ?, ?)";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, proprietaireId);
            ps.setString(2, titre);
            ps.setString(3, description);
            ps.setString(4, adresse);
            ps.setDouble(5, prixParNuit);
            ps.setString(6, codePostal);
            ps.setInt(7, nbChambres);
            ps.setString(8, typeLogement);
            ps.executeUpdate();
            ps.close();
            uneBDD.seDeConnecter();
            ok = true;
        } catch (SQLException exp) {
            System.out.println("Erreur ajout logement : " + exp.getMessage());
        }
        return ok;
    }

    // Ajouter une réservation
    public static boolean reserverLogement(
        int logementId,
        int clientId,
        String nomClient,
        String emailClient,
        String telephone,
        String dateDebut,
        String dateFin,
        int nbPersonnes,
        String commentaire
    ) {
        boolean ok = false;
        String requete = "INSERT INTO reservations (logement_id, client_id, nom_client, email_client, telephone, date_debut, date_fin, nb_personnes, commentaire, statut) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'en_attente')";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, logementId);
            ps.setInt(2, clientId);
            ps.setString(3, nomClient);
            ps.setString(4, emailClient);
            ps.setString(5, telephone);
            ps.setString(6, dateDebut);
            ps.setString(7, dateFin);
            ps.setInt(8, nbPersonnes);
            ps.setString(9, commentaire);
            ps.executeUpdate();
            ps.close();
            uneBDD.seDeConnecter();
            ok = true;
        } catch (SQLException exp) {
            System.out.println("Erreur reservation : " + exp.getMessage());
        }
        return ok;
    }

    // Liste des réservations du client
    public static ArrayList<HashMap<String, String>> getReservationsByClient(int clientId) {
        ArrayList<HashMap<String, String>> liste = new ArrayList<>();
        String requete = "SELECT * FROM reservations WHERE client_id = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HashMap<String, String> res = new HashMap<>();
                res.put("id", rs.getString("id"));
                res.put("logement_id", rs.getString("logement_id"));
                res.put("date_debut", rs.getString("date_debut"));
                res.put("date_fin", rs.getString("date_fin"));
                res.put("nb_personnes", rs.getString("nb_personnes"));
                res.put("commentaire", rs.getString("commentaire"));
                res.put("statut", rs.getString("statut"));
                res.put("date_reservation", rs.getString("date_reservation"));
                liste.add(res);
            }
            ps.close();
            uneBDD.seDeConnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur getReservationsByClient : " + exp.getMessage());
        }
        return liste;
    }

    // Liste des réservations du propriétaire
    public static ArrayList<HashMap<String, String>> getReservationsByProprietaire(int proprietaireId) {
        ArrayList<HashMap<String, String>> liste = new ArrayList<>();
        String requete = "SELECT r.*, l.titre FROM reservations r " +
                         "JOIN logements l ON r.logement_id = l.id " +
                         "WHERE l.proprietaire_id = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setInt(1, proprietaireId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HashMap<String, String> res = new HashMap<>();
                res.put("id", rs.getString("id"));
                res.put("titre_logement", rs.getString("titre"));
                res.put("date_debut", rs.getString("date_debut"));
                res.put("date_fin", rs.getString("date_fin"));
                res.put("nb_personnes", rs.getString("nb_personnes"));
                res.put("statut", rs.getString("statut"));
                res.put("nom_client", rs.getString("nom_client"));
                liste.add(res);
            }
            ps.close();
            uneBDD.seDeConnecter();
        } catch (Exception e) {
            System.out.println("Erreur getReservationsByProprietaire : " + e.getMessage());
        }
        return liste;
    }

    // Modifier statut réservation (accepter/refuser)
    public static void updateReservationStatut(int reservationId, String nouveauStatut) {
        String requete = "UPDATE reservations SET statut = ? WHERE id = ?";
        try {
            uneBDD.seConnecter();
            PreparedStatement ps = uneBDD.getMaConnexion().prepareStatement(requete);
            ps.setString(1, nouveauStatut);
            ps.setInt(2, reservationId);
            ps.executeUpdate();
            ps.close();
            uneBDD.seDeConnecter();
        } catch (Exception e) {
            System.out.println("Erreur updateReservationStatut : " + e.getMessage());
        }
    }
}
