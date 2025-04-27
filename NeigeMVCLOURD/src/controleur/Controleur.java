package controleur;

import vue.vue_logement;
import vue.vue_reservation;

public class Controleur {
    public static void afficherLogements() {
        new vue_logement();
    }

    public static void afficherReservations() {
        new vue_reservation();
    }
}
