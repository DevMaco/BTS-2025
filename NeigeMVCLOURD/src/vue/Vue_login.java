package vue;

import modele.Modele;
import javax.swing.*;
import java.awt.*;

public class Vue_login extends JFrame {
    // Palette
    private static final Color FOND_PRINCIPAL = new Color(0x003161);
    private static final Color FOND_FORM = new Color(0xFFF4B7);
    private static final Color BTN_PRINCIPAL = new Color(0x006A67);
    private static final Color BTN_TXT = Color.WHITE;
    private static final Font FONT_TITRE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_CHAMP = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 18);

    public Vue_login() {
        setTitle("Connexion administrateur");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FOND_PRINCIPAL);

        // Bandeau titre
        JLabel titre = new JLabel("Connexion Admin");
        titre.setFont(FONT_TITRE);
        titre.setForeground(Color.WHITE);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBorder(BorderFactory.createEmptyBorder(35, 10, 20, 10));
        mainPanel.add(titre, BorderLayout.NORTH);

        // Formulaire centré
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(FOND_FORM);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lEmail = new JLabel("Email admin :");
        lEmail.setFont(FONT_LABEL);
        JTextField tfEmail = new JTextField();
        tfEmail.setFont(FONT_CHAMP);

        JLabel lMdp = new JLabel("Mot de passe :");
        lMdp.setFont(FONT_LABEL);
        JPasswordField tfMdp = new JPasswordField();
        tfMdp.setFont(FONT_CHAMP);

        JButton btnLogin = new JButton("Connexion");
        btnLogin.setFont(FONT_BTN);
        btnLogin.setBackground(BTN_PRINCIPAL);
        btnLogin.setForeground(BTN_TXT);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Placement
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        formPanel.add(lEmail, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        formPanel.add(tfEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(lMdp, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        formPanel.add(tfMdp, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 0;
        formPanel.add(btnLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(lblMsg, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> {
            String email = tfEmail.getText();
            String mdp = new String(tfMdp.getPassword());
            if (Modele.connexionAdmin(email, mdp)) {
                lblMsg.setForeground(new Color(0x006A67));
                lblMsg.setText("Connexion réussie !");
                dispose();
                new Vue_menu_admin();
            } else {
                lblMsg.setForeground(Color.RED);
                lblMsg.setText("Email ou mot de passe incorrect.");
            }
        });

        setContentPane(mainPanel);
        setVisible(true);
    }
}
