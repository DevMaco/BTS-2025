package vue;

import modele.Modele;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Vue_reservation extends JFrame {
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

    public Vue_reservation() {
        setTitle("Gestion des réservations");
        setSize(1250, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FOND_PRINCIPAL);

        JLabel titre = new JLabel("Gestion des réservations");
        titre.setFont(FONT_TITRE);
        titre.setForeground(Color.WHITE);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainPanel.add(titre, BorderLayout.NORTH);

        String[] col = {"ID", "Client ID", "Nom", "Prénom", "Email", "Téléphone", "Logement ID", "Début", "Fin", "Nb pers", "Commentaire", "Statut", "Date réservation"};
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
        btnAdd.addActionListener(e -> ajouterReservation());
        btnEdit.addActionListener(e -> modifierReservation());
        btnDelete.addActionListener(e -> supprimerReservation());

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
        ArrayList<String[]> reservations = Modele.getListeReservations();
        for (String[] r : reservations) model.addRow(r);
    }

    private void ajouterReservation() {
        JTextField tfClientId = new JTextField();
        JTextField tfNom = new JTextField();
        JTextField tfPrenom = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfTel = new JTextField();
        JTextField tfLogementId = new JTextField();
        JTextField tfDebut = new JTextField();
        JTextField tfFin = new JTextField();
        JTextField tfNb = new JTextField();
        JTextField tfCom = new JTextField();
        JComboBox<String> cbStatut = new JComboBox<>(new String[]{"en_attente", "acceptee", "annulee"});

        JPanel panel = createFormPanel();
        panel.add(new JLabel("ID Client:")); panel.add(tfClientId);
        panel.add(new JLabel("Nom client:")); panel.add(tfNom);
        panel.add(new JLabel("Prénom:")); panel.add(tfPrenom);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Téléphone:")); panel.add(tfTel);
        panel.add(new JLabel("ID Logement:")); panel.add(tfLogementId);
        panel.add(new JLabel("Date début (YYYY-MM-DD):")); panel.add(tfDebut);
        panel.add(new JLabel("Date fin (YYYY-MM-DD):")); panel.add(tfFin);
        panel.add(new JLabel("Nb personnes:")); panel.add(tfNb);
        panel.add(new JLabel("Commentaire:")); panel.add(tfCom);
        panel.add(new JLabel("Statut:")); panel.add(cbStatut);

        int res = JOptionPane.showConfirmDialog(this, panel, "Ajouter une réservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.ajouterReservation(
                    Integer.parseInt(tfClientId.getText()),
                    tfNom.getText(),
                    tfPrenom.getText(),
                    tfEmail.getText(),
                    tfTel.getText(),
                    Integer.parseInt(tfLogementId.getText()),
                    tfDebut.getText(),
                    tfFin.getText(),
                    Integer.parseInt(tfNb.getText()),
                    tfCom.getText(),
                    cbStatut.getSelectedItem().toString()
            );
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur ajout réservation !");
        }
    }

    private void modifierReservation() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne une réservation"); return; }
        String[] r = new String[13];
        for (int i=0; i<13; i++) r[i]=String.valueOf(model.getValueAt(row,i));

        JTextField tfClientId = new JTextField(r[1]);
        JTextField tfNom = new JTextField(r[2]);
        JTextField tfPrenom = new JTextField(r[3]);
        JTextField tfEmail = new JTextField(r[4]);
        JTextField tfTel = new JTextField(r[5]);
        JTextField tfLogementId = new JTextField(r[6]);
        JTextField tfDebut = new JTextField(r[7]);
        JTextField tfFin = new JTextField(r[8]);
        JTextField tfNb = new JTextField(r[9]);
        JTextField tfCom = new JTextField(r[10]);
        JComboBox<String> cbStatut = new JComboBox<>(new String[]{"en_attente", "acceptee", "annulee"});
        cbStatut.setSelectedItem(r[11]);

        JPanel panel = createFormPanel();
        panel.add(new JLabel("ID Client:")); panel.add(tfClientId);
        panel.add(new JLabel("Nom client:")); panel.add(tfNom);
        panel.add(new JLabel("Prénom:")); panel.add(tfPrenom);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Téléphone:")); panel.add(tfTel);
        panel.add(new JLabel("ID Logement:")); panel.add(tfLogementId);
        panel.add(new JLabel("Date début (YYYY-MM-DD):")); panel.add(tfDebut);
        panel.add(new JLabel("Date fin (YYYY-MM-DD):")); panel.add(tfFin);
        panel.add(new JLabel("Nb personnes:")); panel.add(tfNb);
        panel.add(new JLabel("Commentaire:")); panel.add(tfCom);
        panel.add(new JLabel("Statut:")); panel.add(cbStatut);

        int res = JOptionPane.showConfirmDialog(this, panel, "Modifier la réservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            boolean ok = Modele.majReservation(
                    Integer.parseInt(r[0]),
                    Integer.parseInt(tfClientId.getText()),
                    tfNom.getText(),
                    tfPrenom.getText(),
                    tfEmail.getText(),
                    tfTel.getText(),
                    Integer.parseInt(tfLogementId.getText()),
                    tfDebut.getText(),
                    tfFin.getText(),
                    Integer.parseInt(tfNb.getText()),
                    tfCom.getText(),
                    cbStatut.getSelectedItem().toString()
            );
            if (ok) refreshTable();
            else JOptionPane.showMessageDialog(this, "Erreur modification !");
        }
    }

    private void supprimerReservation() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionne une réservation"); return; }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int res = JOptionPane.showConfirmDialog(this, "Supprimer cette réservation ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            boolean ok = Modele.supprimerReservation(id);
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
