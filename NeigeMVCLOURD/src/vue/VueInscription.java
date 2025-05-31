package vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import modele.Modele;

public class VueInscription extends JPanel {
    public VueInscription() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNom = new JLabel("Nom :");
        JTextField fieldNom = new JTextField(16);

        JLabel labelPrenom = new JLabel("Prénom :");
        JTextField fieldPrenom = new JTextField(16);

        JLabel labelEmail = new JLabel("Email :");
        JTextField fieldEmail = new JTextField(18);

        JLabel labelTel = new JLabel("Téléphone :");
        JTextField fieldTel = new JTextField(12);

        JLabel labelMdp = new JLabel("Mot de passe :");
        JPasswordField fieldMdp = new JPasswordField(16);

        JLabel labelType = new JLabel("Type :");
        String[] types = {"client", "proprietaire"};
        JComboBox<String> cbType = new JComboBox<>(types);

        JButton btnInscrire = new JButton("S'inscrire");
        JButton btnAnnuler = new JButton("Annuler");

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(labelNom, gbc); gbc.gridx = 1; this.add(fieldNom, gbc);
        gbc.gridx = 0; gbc.gridy++; this.add(labelPrenom, gbc); gbc.gridx = 1; this.add(fieldPrenom, gbc);
        gbc.gridx = 0; gbc.gridy++; this.add(labelEmail, gbc); gbc.gridx = 1; this.add(fieldEmail, gbc);
        gbc.gridx = 0; gbc.gridy++; this.add(labelTel, gbc); gbc.gridx = 1; this.add(fieldTel, gbc);
        gbc.gridx = 0; gbc.gridy++; this.add(labelMdp, gbc); gbc.gridx = 1; this.add(fieldMdp, gbc);
        gbc.gridx = 0; gbc.gridy++; this.add(labelType, gbc); gbc.gridx = 1; this.add(cbType, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        this.add(btnInscrire, gbc);
        gbc.gridy++;
        this.add(btnAnnuler, gbc);

        btnInscrire.addActionListener(e -> {
            HashMap<String, String> utilisateur = new HashMap<>();
            utilisateur.put("nom", fieldNom.getText().trim());
            utilisateur.put("email", fieldEmail.getText().trim());
            utilisateur.put("telephone", fieldTel.getText().trim());
            utilisateur.put("mdp", new String(fieldMdp.getPassword()).trim());
            utilisateur.put("type", cbType.getSelectedItem().toString());

            boolean ok = Modele.inscrireUtilisateur(utilisateur);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(new VueConnexion());
                topFrame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : email déjà utilisé.");
            }
        });

        btnAnnuler.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new VueConnexion());
            topFrame.revalidate();
        });
    }
}
