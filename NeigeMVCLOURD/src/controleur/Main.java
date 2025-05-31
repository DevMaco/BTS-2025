package controleur;

import javax.swing.JFrame;
import vue.VueConnexion;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NeigeSoleil MVC LOURD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(new VueConnexion());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
