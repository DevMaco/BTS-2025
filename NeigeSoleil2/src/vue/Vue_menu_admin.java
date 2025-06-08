package vue;

import javax.swing.*;
import java.awt.*;

public class Vue_menu_admin extends JFrame {
    public Vue_menu_admin() {
        setTitle("Menu Administrateur");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Couleur principale et police
        Color bg = new Color(0x003161);
        Color btnColor = new Color(0x006A67);
        Color txtColor = Color.WHITE;
        Font fontTitre = new Font("Segoe UI", Font.BOLD, 28);
        Font fontBtn = new Font("Segoe UI", Font.BOLD, 18);

        JPanel panel = new JPanel();
        panel.setBackground(bg);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 70, 40, 70));

        JLabel titre = new JLabel("Espace Administration");
        titre.setFont(fontTitre);
        titre.setForeground(txtColor);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnUsers = createStyledButton("Gestion des utilisateurs", btnColor, txtColor, fontBtn);
        JButton btnLogements = createStyledButton("Gestion des logements", btnColor, txtColor, fontBtn);
        JButton btnReservations = createStyledButton("Gestion des réservations", btnColor, txtColor, fontBtn);

        panel.add(titre);
        panel.add(Box.createVerticalStrut(40));
        panel.add(btnUsers);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnLogements);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnReservations);

        btnUsers.addActionListener(e -> new Vue_user());
        btnLogements.addActionListener(e -> new Vue_logement());
        btnReservations.addActionListener(e -> new Vue_reservation());

        setContentPane(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg, Color fg, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        return btn;
    }
}
