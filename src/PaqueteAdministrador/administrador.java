package PaqueteAdministrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class administrador {
    public JPanel PAdministrador;
    private JButton gestionDeUsuariosButton;
    private JButton estadisticasButton;
    private JButton establecerConfiguracionesButton;
    private JButton regresarButton;

    public administrador() {

        JFrame menuFrame = new JFrame("Pantalla de Administrador");
        menuFrame.setContentPane(PAdministrador);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.pack();
        menuFrame.setVisible(true);

        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new gestionUsuarios();
            }
        });
    }
}
