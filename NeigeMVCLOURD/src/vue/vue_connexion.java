package vue;

import javax.swing.*;
import java.awt.*;
import modele.Modele;

public class vue_connexion extends JFrame {
    private JTextField txtUrl, txtUser;
    private JPasswordField txtMdp;

    public vue_connexion() {
        setTitle("Connexion à la base");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtUrl = new JTextField("jdbc:mysql://localhost/neige");
        txtUser = new JTextField("root");
        txtMdp = new JPasswordField("");

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> {
            String url = txtUrl.getText();
            String user = txtUser.getText();
            String mdp = new String(txtMdp.getPassword());

            Modele.connecter(url, user, mdp);
            if (Modele.getConnexion() != null) {
                dispose();
                new vue_accueil();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la connexion", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new GridLayout(4, 2));
        add(new JLabel("URL :"));
        add(txtUrl);
        add(new JLabel("Utilisateur :"));
        add(txtUser);
        add(new JLabel("Mot de passe :"));
        add(txtMdp);
        add(new JLabel(""));
        add(btnConnexion);

        setVisible(true);
    }
}
