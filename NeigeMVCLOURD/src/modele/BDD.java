package modele;
import java.sql.*;

public class BDD {
    private String user, mdp, url, db;
    private Connection maConnexion;

    public BDD(String user, String mdp, String url, String db) {
        this.user = user;
        this.mdp = mdp;
        this.url = url;
        this.db = db;
        this.maConnexion = null;
    }

    public void seConnecter() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.maConnexion = DriverManager.getConnection(
                "jdbc:mysql://" + this.url + "/" + this.db + "?serverTimezone=UTC",
                this.user,
                this.mdp
            );
        } catch (Exception exp) {
            System.out.println("Erreur de connexion BDD : " + exp);
        }
    }

    public void seDeConnecter() {
        try {
            if (this.maConnexion != null && !this.maConnexion.isClosed()) {
                this.maConnexion.close();
            }
        } catch (SQLException exp) {
            System.out.println("Erreur de déconnexion : " + exp);
        }
    }

    public Connection getMaConnexion() {
        return this.maConnexion;
    }
}
