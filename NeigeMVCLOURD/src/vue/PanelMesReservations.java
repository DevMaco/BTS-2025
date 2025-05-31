package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import modele.Modele;
import controleur.Session;

public class PanelMesReservations extends JPanel {
    public PanelMesReservations() {
        this.setLayout(new BorderLayout());

        JLabel titre = new JLabel("Mes réservations", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Logement", "Début", "Fin", "Statut"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        ArrayList<HashMap<String, String>> reservations = Modele.getReservationsByClient(Session.idUser);
        for (HashMap<String, String> res : reservations) {
            model.addRow(new Object[]{
                res.get("id"),
                res.get("logement_id"),
                res.get("date_debut"),
                res.get("date_fin"),
                res.get("statut")
            });
        }

        JTable table = new JTable(model);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRetour = new JButton("Retour menu");
        btnRetour.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PanelPrincipal());
            topFrame.revalidate();
        });

        JPanel panelSouth = new JPanel();
        panelSouth.add(btnRetour);
        this.add(panelSouth, BorderLayout.SOUTH);
    }
}
