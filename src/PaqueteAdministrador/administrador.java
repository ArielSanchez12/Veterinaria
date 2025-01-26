package PaqueteAdministrador;
import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class administrador {
    public JPanel PAdministrador;
    private JButton gestionDeUsuariosButton;
    private JButton estadisticasButton;
    private JButton regresarButton;

    public administrador() {

        JFrame menuFrame = new JFrame("Pantalla de Administrador");
        menuFrame.setContentPane(PAdministrador);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/perfil-administrador.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);

        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new gestionUsuarios();
            }
        });

        estadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new estadisticas();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });

    }
}
