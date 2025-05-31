package vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import modele.Modele;
import controleur.Session;

public class VueConnexion extends JPanel {
    public VueConnexion() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelEmail = new JLabel("Email :");
        JTextField fieldEmail = new JTextField(20);

        JLabel labelMdp = new JLabel("Mot de passe :");
        JPasswordField fieldMdp = new JPasswordField(20);

        JButton btnConnexion = new JButton("Connexion");
        JButton btnInscription = new JButton("Créer un compte");

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(labelEmail, gbc);
        gbc.gridx = 1;
        this.add(fieldEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(labelMdp, gbc);
        gbc.gridx = 1;
        this.add(fieldMdp, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        this.add(btnConnexion, gbc);

        gbc.gridy = 3;
        this.add(btnInscription, gbc);

        btnConnexion.addActionListener(e -> {
            String email = fieldEmail.getText().trim();
            String mdp = new String(fieldMdp.getPassword()).trim();

            HashMap<String, String> user = Modele.connexionUtilisateur(email, mdp);
            if (user != null) {
                Session.idUser = Integer.parseInt(user.get("id"));
                Session.nom = user.get("nom");
                Session.email = user.get("email");
                Session.telephone = user.get("telephone");
                Session.type = user.get("type");
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(new PanelPrincipal());
                topFrame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants invalides !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnInscription.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new VueInscription());
            topFrame.revalidate();
        });
    }
}
