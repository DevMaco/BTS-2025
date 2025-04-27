package vue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modele.Modele;
import modele.Logement;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class vue_logement extends JFrame {
    private JList<Logement> lstLogements;
    private DefaultListModel<Logement> modelListe;

    public vue_logement() {
        setTitle("Gestion des logements");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelListe = new DefaultListModel<>();
        lstLogements = new JList<>(modelListe);
        JScrollPane scrollPane = new JScrollPane(lstLogements);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        btnAjouter.addActionListener(e -> ajouterLogement());
        btnModifier.addActionListener(e -> modifierLogement());
        btnSupprimer.addActionListener(e -> supprimerLogement());

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        chargerLogements();
        setVisible(true);
    }

    private void chargerLogements() {
        modelListe.clear();
        List<Logement> logements = Modele.getAllLogements();
        for (Logement l : logements) {
            modelListe.addElement(l);
        }
    }

    private void ajouterLogement() {
        JTextField nom = new JTextField();
        JTextField adresse = new JTextField();
        JTextField prix = new JTextField();
        Object[] fields = {
            "Nom :", nom,
            "Adresse :", adresse,
            "Prix :", prix
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Ajouter un logement", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Logement l = new Logement(0, nom.getText(), adresse.getText(), Double.parseDouble(prix.getText()));
                Modele.ajouterLogement(l);
                chargerLogements();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierLogement() {
        Logement selection = lstLogements.getSelectedValue();
        if (selection == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un logement");
            return;
        }
        JTextField nom = new JTextField(selection.getNom());
        JTextField adresse = new JTextField(selection.getAdresse());
        JTextField prix = new JTextField(String.valueOf(selection.getPrix()));
        Object[] fields = {
            "Nom :", nom,
            "Adresse :", adresse,
            "Prix :", prix
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Modifier le logement", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                selection.setNom(nom.getText());
                selection.setAdresse(adresse.getText());
                selection.setPrix(Double.parseDouble(prix.getText()));
                Modele.modifierLogement(selection);
                chargerLogements();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerLogement() {
        Logement selection = lstLogements.getSelectedValue();
        if (selection != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce logement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Modele.supprimerLogement(selection.getId());
                chargerLogements();
            }
        }
    }
}
