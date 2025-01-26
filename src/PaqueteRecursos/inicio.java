package PaqueteRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inicio {
    public JPanel PInicio;
    private JButton registroParaClientesNuevosButton;
    private JButton inicioDeSesiónButton;
    private JButton salirButton;

    public inicio() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(PInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame menuFrame = new JFrame("Inicio");
        menuFrame.setContentPane(PInicio);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/pet-care-pro.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);

        registroParaClientesNuevosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registro();
                menuFrame.dispose();
            }
        });
        inicioDeSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inicioFrame = (JFrame) SwingUtilities.getWindowAncestor(PInicio);
                if (inicioFrame != null) {
                    inicioFrame.dispose();
                }
                System.exit(0);
            }
        });
    }
}
