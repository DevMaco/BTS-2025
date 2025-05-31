package controleur;

import java.awt.LayoutManager;

public class Session {
    public static int idUser = 0;
    public static String nom = "";
    public static String email = "";
    public static String telephone = "";
    public static String type = ""; // "client" ou "proprietaire"
	

    public static void clear() {
        idUser = 0;
        nom = "";
        email = "";
        telephone = "";
        type = "";
    }
}
