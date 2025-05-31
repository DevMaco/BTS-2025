package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controleur.Session;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import modele.Modele;

public class PanelLogement extends JPanel {
    public PanelLogement() {
        this.setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Liste des logements", JLabel.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 22));
        this.add(labelTitre, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Adresse"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        ArrayList<HashMap<String, String>> logements = Modele.getLogements();
        for (HashMap<String, String> log : logements) {
            model.addRow(new Object[]{log.get("id"), log.get("nom"), log.get("adresse")});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBtns = new JPanel();
        JButton btnReserver = new JButton("Réserver");
        JButton btnRetour = new JButton("Retour menu");
        if ("client".equalsIgnoreCase(Session.type)) {
            panelBtns.add(btnReserver);
        }
        panelBtns.add(btnRetour);

        this.add(panelBtns, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new PanelPrincipal());
            topFrame.revalidate();
        });

        btnReserver.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionne un logements !");
            } else {
                int logementId = Integer.parseInt(table.getValueAt(row, 0).toString());
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(
                    new PanelReservation(
                        logementId,
                        Session.idUser,
                        Session.nom,
                        Session.email,
                        Session.telephone
                    )
                );
                topFrame.revalidate();
            }
        });
    }
}
