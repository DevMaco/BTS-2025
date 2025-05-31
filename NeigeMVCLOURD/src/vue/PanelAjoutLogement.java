package vue;

import javax.swing.*;
import java.awt.*;
import modele.Modele;

public class PanelAjoutLogement extends JPanel {
    private JTextField txtTitre, txtDescription, txtAdresse, txtPrix, txtCodePostal, txtNbChambres, txtType;
    private JButton btnAjouter;
    private int proprietaireId;

    public PanelAjoutLogement(int proprietaireId) {
        this.proprietaireId = proprietaireId;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Titre :"), gbc);
        gbc.gridx = 1; txtTitre = new JTextField(16);
        this.add(txtTitre, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Description :"), gbc);
        gbc.gridx = 1; txtDescription = new JTextField(16);
        this.add(txtDescription, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1; txtAdresse = new JTextField(16);
        this.add(txtAdresse, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Prix par nuit (€) :"), gbc);
        gbc.gridx = 1; txtPrix = new JTextField(6);
        this.add(txtPrix, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Code postal :"), gbc);
        gbc.gridx = 1; txtCodePostal = new JTextField(8);
        this.add(txtCodePostal, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Nombre de chambres :"), gbc);
        gbc.gridx = 1; txtNbChambres = new JTextField(2);
        this.add(txtNbChambres, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row;
        this.add(new JLabel("Type de logement :"), gbc);
        gbc.gridx = 1; txtType = new JTextField(12);
        this.add(txtType, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        btnAjouter = new JButton("Ajouter le logement");
        this.add(btnAjouter, gbc);

        btnAjouter.addActionListener(e -> ajouterAction());
    }

    private void ajouterAction() {
        String titre = txtTitre.getText().trim();
        String description = txtDescription.getText().trim();
        String adresse = txtAdresse.getText().trim();
        String prixStr = txtPrix.getText().trim();
        String codePostal = txtCodePostal.getText().trim();
        String nbChambresStr = txtNbChambres.getText().trim();
        String typeLogement = txtType.getText().trim();

        if (titre.isEmpty() || description.isEmpty() || adresse.isEmpty() || prixStr.isEmpty() ||
            codePostal.isEmpty() || nbChambresStr.isEmpty() || typeLogement.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        double prix;
        int nbChambres;
        try {
            prix = Double.parseDouble(prixStr);
            nbChambres = Integer.parseInt(nbChambresStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Prix ou nombre de chambres invalide !");
            return;
        }

        boolean ok = Modele.ajouterLogement(
            proprietaireId, titre, description, adresse,
            prix, codePostal, nbChambres, typeLogement
        );
        if (ok) {
            JOptionPane.showMessageDialog(this, "Logement ajouté !");
            // reset les champs
            txtTitre.setText("");
            txtDescription.setText("");
            txtAdresse.setText("");
            txtPrix.setText("");
            txtCodePostal.setText("");
            txtNbChambres.setText("");
            txtType.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.");
        }
    }
}
