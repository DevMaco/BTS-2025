package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import modele.Modele;
import controleur.Session;

public class PanelListeReservationsProprio extends JPanel {
    public PanelListeReservationsProprio() {
        this.setLayout(new BorderLayout());

        JLabel titre = new JLabel("Réservations reçues sur mes logements", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Logement", "Début", "Fin", "Nb pers.", "Statut", "Nom client"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        ArrayList<HashMap<String, String>> reservations = Modele.getReservationsByProprietaire(Session.idUser);

        for (HashMap<String, String> res : reservations) {
            model.addRow(new Object[]{
                res.get("id"),
                res.get("titre_logement"),
                res.get("date_debut"),
                res.get("date_fin"),
                res.get("nb_personnes"),
                res.get("statut"),
                res.get("nom_client")
            });
        }

        JTable table = new JTable(model);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelSouth = new JPanel();
        JButton btnAccepter = new JButton("Accepter");
        JButton btnRefuser = new JButton("Refuser");
        JButton btnRetour = new JButton("Retour menu");

        panelSouth.add(btnAccepter);
        panelSouth.add(btnRefuser);
        panelSouth.add(btnRetour);
        this.add(panelSouth, BorderLayout.SOUTH);

        btnAccepter.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionne une réservation !");
            } else {
                String statut = table.getValueAt(row, 5).toString();
                if (!statut.equals("en_attente")) {
                    JOptionPane.showMessageDialog(this, "La réservation n'est plus modifiable.");
                } else {
                    int idRes = Integer.parseInt(table.getValueAt(row, 0).toString());
                    Modele.updateReservationStatut(idRes, "acceptee");
                    JOptionPane.showMessageDialog(this, "Réservation acceptée !");
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    topFrame.setContentPane(new PanelListeReservationsProprio());
                    topFrame.revalidate();
                }
            }
        });

        btnRefuser.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionne une réservation !");
            } else {
                String statut = table.getValueAt(row, 5).toString();
                if (!statut.equals("en_attente")) {
                    JOptionPane.showMessageDialog(this, "La réservation n'est plus modifiable.");
                } else {
                    int idRes = Integer.parseInt(table.getValueAt(row, 0).toString());
                    Modele.updateReservationStatut(idRes, "refusee");
                    JOptionPane.showMessageDialog(this, "Réservation refusée !");
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    topFrame.setContentPane(new PanelListeReservationsProprio());
                    topFrame.revalidate();
                }
            }
        });

        btnRetour.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PanelPrincipal());
            topFrame.revalidate();
        });
    }
}
