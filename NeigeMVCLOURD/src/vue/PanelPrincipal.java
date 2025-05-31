package vue;

import javax.swing.*;
import java.awt.*;
import controleur.Session;

public class PanelPrincipal extends JPanel {
    public PanelPrincipal() {
        this.setLayout(new FlowLayout());

        JButton btnLogements = new JButton("Voir les logements");
        JButton btnReservations = new JButton("Mes réservations");
        JButton btnDeconnexion = new JButton("Déconnexion");

        this.add(btnLogements);
        this.add(btnReservations);

        // Si propriétaire : ajoute bouton Ajout Logement et Réservations reçues
        if ("proprietaire".equalsIgnoreCase(Session.type)) {
            JButton btnAjouterLogement = new JButton("Ajouter un logement");
            this.add(btnAjouterLogement);

            btnAjouterLogement.addActionListener(e -> {
                // Ouvre le panel d'ajout dans une popup
                PanelAjoutLogement panelAjout = new PanelAjoutLogement(Session.idUser); // Session.id = id du proprio
                JFrame popup = new JFrame("Ajouter un logement");
                popup.setContentPane(panelAjout);
                popup.pack();
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            });

            JButton btnResasProprio = new JButton("Réservations reçues");
            this.add(btnResasProprio);
            btnResasProprio.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(new PanelListeReservationsProprio());
                topFrame.revalidate();
            });
        }

        this.add(btnDeconnexion);

        btnLogements.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PanelLogement());
            topFrame.revalidate();
        });

        btnReservations.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PanelMesReservations());
            topFrame.revalidate();
        });

        btnDeconnexion.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Session.clear();
            topFrame.setContentPane(new VueConnexion());
            topFrame.revalidate();
        });
    }
}
