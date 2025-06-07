package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {
    private static final String URL = "jdbc:mysql://localhost:3306/neige";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // à adapter si tu as un mdp

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
