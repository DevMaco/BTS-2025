package app;

import javax.swing.SwingUtilities;
import vue.vue_connexion;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new vue_connexion();
        });
    }
}
