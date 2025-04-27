package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controleur.Controleur;

public class vue_accueil extends JFrame {
    public vue_accueil() {
        setTitle("Neige - Accueil");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnLogements = new JButton("Gestion des logements");
        JButton btnReservations = new JButton("Gestion des réservations");

        btnLogements.addActionListener(e -> Controleur.afficherLogements());
        btnReservations.addActionListener(e -> Controleur.afficherReservations());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(btnLogements);
        panel.add(btnReservations);

        add(panel);
        setVisible(true);
    }
}
