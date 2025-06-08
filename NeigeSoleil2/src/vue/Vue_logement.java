package vue;

import modele.Modele;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Vue_logement extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    // Palette IrisEvent
    private static final Color FOND_PRINCIPAL = new Color(0x003161);
    private static final Color FOND_FORM = new Color(0xFFF4B7);
    private static final Color BTN_PRINCIPAL = new Color(0x006A67);
    private static final Color BTN_TXT = Color.WHITE;
    private static final Font FONT_TITRE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 15);

    public Vue_logement() {
        setTitle("Gestion des logements");
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel avec fond coloré
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FOND_PRINCIPAL);

        // Bandeau titre
        JLabel titre = new JLabel("Gestion des logements");
        titre.setFont(FONT_TITRE);
        titre.setForeground(Color.WHITE);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(titre, BorderLayout.NORTH);

        // Table
        String[] col = {"ID", "Proprio", "Titre", "Description", "Adresse", "Prix/Nuit", "Disponible", "CP", "Chambres", "Type"};
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

        // Panel boutons
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(FOND_PRINCIPAL);
        panelBtn.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnAdd = createStyledButton("Ajouter un logement");
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
        btnAdd.addActionListener(e -> ajouterLogement());
        btnEdit.addActionListener(e -> modifierLogement());
        btnDelete.addActionListener(e -> supprimerLogement());

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
        ArrayList<String[]> logements = Modele.getListeLogements();
        for (String[] l : logements) model.addRow(l);
    }

    private void ajouterLogement() {
        JTextField tfProprio = new JTextField();
        JTextField tfTitre = new JTextField();
        JTextField tfDesc = new JTextField();
        JTextField tfAdresse = new JTextField();
        JTextField tfPrix = new JTextField();
        JCheckBox cbDispo = new JCheckBox("Disponible");
        JTextField tfCP = new JTextField();
        JTextField tfChambres = new JTextField();
        JTextField tfType = new JTextField();

        JPanel panel = createFormPanel();
        panel.add(new JLabel("ID Propriétaire:")); panel.add(tfProprio);
        panel.add(new JLabel("Titre:")); panel.add(tfTitre);
        panel.add(new JLabel("Description:")); panel.add(tfDesc);
        panel.add(new JLabel("Adresse:")); panel.add(tfAdresse);
        panel.add(new JLabel("Prix par nuit:")); panel.add(tfPrix);
        panel.add(new JLabel("Disponible:")); panel.add(cbDispo);
        panel.add(new JLabel("Code Postal:")); panel.add(tfCP);
        panel.add(new JLabel("Nb Chambres:")); panel.add(tfChambres);
        panel.add(new JLabel("Type logement:")); panel.add(tfType);

        int res = JOptionPane.showConfirmDialog(this, panel, "Ajouter un logement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.ajouterLogement(
                Integer.parseInt(tfProprio.getText()),
                tfTitre.getText(),
                tfDesc.getText(),
                tfAdresse.getText(),
                Double.parseDouble(tfPrix.getText()),
                cbDispo.isSelected(),
                tfCP.getText(),
                Integer.parseInt(tfChambres.getText()),
                tfType.getText()
            );
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur ajout logement !");
        }
    }

    private void modifierLogement() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne un logement"); return; }
        String[] l = new String[10];
        for (int i=0; i<10; i++) l[i]=String.valueOf(model.getValueAt(row,i));

        JTextField tfProprio = new JTextField(l[1]);
        JTextField tfTitre = new JTextField(l[2]);
        JTextField tfDesc = new JTextField(l[3]);
        JTextField tfAdresse = new JTextField(l[4]);
        JTextField tfPrix = new JTextField(l[5]);
        JCheckBox cbDispo = new JCheckBox("Disponible");
        cbDispo.setSelected(l[6].equals("true") || l[6].equals("1"));
        JTextField tfCP = new JTextField(l[7]);
        JTextField tfChambres = new JTextField(l[8]);
        JTextField tfType = new JTextField(l[9]);

        JPanel panel = createFormPanel();
        panel.add(new JLabel("ID Propriétaire:")); panel.add(tfProprio);
        panel.add(new JLabel("Titre:")); panel.add(tfTitre);
        panel.add(new JLabel("Description:")); panel.add(tfDesc);
        panel.add(new JLabel("Adresse:")); panel.add(tfAdresse);
        panel.add(new JLabel("Prix par nuit:")); panel.add(tfPrix);
        panel.add(new JLabel("Disponible:")); panel.add(cbDispo);
        panel.add(new JLabel("Code Postal:")); panel.add(tfCP);
        panel.add(new JLabel("Nb Chambres:")); panel.add(tfChambres);
        panel.add(new JLabel("Type logement:")); panel.add(tfType);

        int res = JOptionPane.showConfirmDialog(this, panel, "Modifier le logement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.majLogement(
                Integer.parseInt(l[0]),
                Integer.parseInt(tfProprio.getText()),
                tfTitre.getText(),
                tfDesc.getText(),
                tfAdresse.getText(),
                Double.parseDouble(tfPrix.getText()),
                cbDispo.isSelected(),
                tfCP.getText(),
                Integer.parseInt(tfChambres.getText()),
                tfType.getText()
            );
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur modification !");
        }
    }

    private void supprimerLogement() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne un logement"); return; }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int res = JOptionPane.showConfirmDialog(this, "Supprimer ce logement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            boolean ok = Modele.supprimerLogement(id);
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur suppression !");
        }
    }

    // Utilitaire : panneau de formulaire coloré et espacé
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(FOND_FORM);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }
}
