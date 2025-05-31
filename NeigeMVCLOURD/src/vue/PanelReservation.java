package vue;

import javax.swing.*;
import java.awt.*;
import modele.Modele;

public class PanelReservation extends JPanel {
    private int logementId;
    private int clientId;
    private JTextField txtNom, txtEmail, txtTelephone, txtDateDebut, txtDateFin, txtNbPers, txtCommentaire;
    private JButton btnReserver, btnRetour;

    public PanelReservation(int clientId, int logementId, String nom, String email, String telephone) {
        this.logementId = logementId;
        this.clientId = clientId;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel lblTitre = new JLabel("Réservation du logement n°" + logementId);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(lblTitre, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Nom client :"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField(nom != null ? nom : "", 16);
        this.add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        this.add(new JLabel("Email client :"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(email != null ? email : "", 16);
        this.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        this.add(new JLabel("Téléphone :"), gbc);
        gbc.gridx = 1;
        txtTelephone = new JTextField(telephone != null ? telephone : "", 16);
        this.add(txtTelephone, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        this.add(new JLabel("Date début (YYYY-MM-DD) :"), gbc);
        gbc.gridx = 1;
        txtDateDebut = new JTextField(12);
        this.add(txtDateDebut, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        this.add(new JLabel("Date fin (YYYY-MM-DD) :"), gbc);
        gbc.gridx = 1;
        txtDateFin = new JTextField(12);
        this.add(txtDateFin, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        this.add(new JLabel("Nombre de personnes :"), gbc);
        gbc.gridx = 1;
        txtNbPers = new JTextField(2);
        this.add(txtNbPers, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        this.add(new JLabel("Commentaire (optionnel) :"), gbc);
        gbc.gridx = 1;
        txtCommentaire = new JTextField(18);
        this.add(txtCommentaire, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        btnReserver = new JButton("Réserver");
        this.add(btnReserver, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        btnRetour = new JButton("Retour à l'accueil");
        this.add(btnRetour, gbc);

        btnReserver.addActionListener(e -> reserverAction());
        btnRetour.addActionListener(e -> retourAccueil());
    }

    private void reserverAction() {
        String nomClient = txtNom.getText().trim();
        String emailClient = txtEmail.getText().trim();
        String telephone = txtTelephone.getText().trim();
        String dateDebut = txtDateDebut.getText().trim();
        String dateFin = txtDateFin.getText().trim();
        String commentaire = txtCommentaire.getText().trim();
        int nbPersonnes = 1;
        try {
            nbPersonnes = Integer.parseInt(txtNbPers.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre valide pour le nombre de personnes.");
            return;
        }

        if (nomClient.isEmpty() || emailClient.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        boolean ok = Modele.reserverLogement(
                logementId, clientId, nomClient, emailClient, telephone,
                dateDebut, dateFin, nbPersonnes, commentaire
        );
        if (ok) {
            JOptionPane.showMessageDialog(this, "Réservation enregistrée avec succès !");
            retourAccueil();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation.");
        }
    }

    private void retourAccueil() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.setContentPane(new PanelPrincipal());
        topFrame.revalidate();
    }
}
