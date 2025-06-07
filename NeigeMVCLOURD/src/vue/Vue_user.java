package vue;

import modele.Modele;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Vue_user extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    // Palette
    private static final Color FOND_PRINCIPAL = new Color(0x003161);
    private static final Color FOND_FORM = new Color(0xFFF4B7);
    private static final Color BTN_PRINCIPAL = new Color(0x006A67);
    private static final Color BTN_TXT = Color.WHITE;
    private static final Font FONT_TITRE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 15);

    public Vue_user() {
        setTitle("Gestion des utilisateurs");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FOND_PRINCIPAL);

        JLabel titre = new JLabel("Gestion des utilisateurs");
        titre.setFont(FONT_TITRE);
        titre.setForeground(Color.WHITE);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(titre, BorderLayout.NORTH);

        String[] col = {"ID", "Nom", "Email", "Mot de passe", "Type"};
        model = new DefaultTableModel(col, 0);
        table = new JTable(model);
        table.setFont(FONT_TABLE);
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setSelectionBackground(new Color(0x006A67));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(FOND_PRINCIPAL);
        panelBtn.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JButton btnAdd = createStyledButton("Ajouter");
        JButton btnEdit = createStyledButton("Modifier");
        JButton btnDelete = createStyledButton("Supprimer");
        JButton btnRefresh = createStyledButton("Actualiser");
        panelBtn.add(btnAdd);
        panelBtn.add(Box.createHorizontalStrut(15));
        panelBtn.add(btnEdit);
        panelBtn.add(Box.createHorizontalStrut(15));
        panelBtn.add(btnDelete);
        panelBtn.add(Box.createHorizontalStrut(15));
        panelBtn.add(btnRefresh);
        mainPanel.add(panelBtn, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshTable());
        btnAdd.addActionListener(e -> ajouterUser());
        btnEdit.addActionListener(e -> modifierUser());
        btnDelete.addActionListener(e -> supprimerUser());

        refreshTable();
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(BTN_PRINCIPAL);
        btn.setForeground(BTN_TXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        return btn;
    }

    private void refreshTable() {
        model.setRowCount(0);
        ArrayList<String[]> users = Modele.getListeUsers();
        for (String[] u : users) model.addRow(u);
    }

    private void ajouterUser() {
        JTextField tfNom = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfMdp = new JTextField();
        JComboBox<String> cbType = new JComboBox<>(new String[]{"client", "proprietaire", "admin"});

        JPanel panel = createFormPanel();
        panel.add(new JLabel("Nom:")); panel.add(tfNom);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Mot de passe:")); panel.add(tfMdp);
        panel.add(new JLabel("Type:")); panel.add(cbType);

        int res = JOptionPane.showConfirmDialog(this, panel, "Ajouter un utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.ajouterUser(tfNom.getText(), tfEmail.getText(), tfMdp.getText(), cbType.getSelectedItem().toString());
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur ajout utilisateur !");
        }
    }

    private void modifierUser() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne un utilisateur"); return; }
        String[] u = new String[5];
        for (int i=0; i<5; i++) u[i]=String.valueOf(model.getValueAt(row,i));

        JTextField tfNom = new JTextField(u[1]);
        JTextField tfEmail = new JTextField(u[2]);
        JTextField tfMdp = new JTextField(u[3]);
        JComboBox<String> cbType = new JComboBox<>(new String[]{"client", "proprietaire", "admin"});
        cbType.setSelectedItem(u[4]);

        JPanel panel = createFormPanel();
        panel.add(new JLabel("Nom:")); panel.add(tfNom);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Mot de passe:")); panel.add(tfMdp);
        panel.add(new JLabel("Type:")); panel.add(cbType);

        int res = JOptionPane.showConfirmDialog(this, panel, "Modifier l'utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.majUser(
                    Integer.parseInt(u[0]), tfNom.getText(), tfEmail.getText(), tfMdp.getText(), cbType.getSelectedItem().toString());
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur modification !");
        }
    }

    private void supprimerUser() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne un utilisateur"); return; }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int res = JOptionPane.showConfirmDialog(this, "Supprimer cet utilisateur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            boolean ok = Modele.supprimerUser(id);
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur suppression !");
        }
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(FOND_FORM);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }
}
