package PaqueteAdministrador;
import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class administrador {
    private JButton gestionDeUsuariosButton;
    private JButton estadisticasButton;
    private JButton regresarButton;

    public administrador() {

        JFrame menuFrame = new JFrame("Menu Administrador");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menuFrame.setSize(500, 300);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/perfil-administrador.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa

        // Panel principal con fondo
        JPanel PAdministrador = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/admin.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PAdministrador.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gestionDeUsuariosButton = new JButton("Gestion de Usuarios");
        gestionDeUsuariosButton.setFont(new Font("Arial", Font.BOLD, 18));
        gestionDeUsuariosButton.setPreferredSize(new Dimension(250, 50));

        estadisticasButton = new JButton("Estadisticas");
        estadisticasButton.setFont(new Font("Arial", Font.BOLD, 18));
        estadisticasButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Cerrar Sesion");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 1; PAdministrador.add(gestionDeUsuariosButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PAdministrador.add(estadisticasButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PAdministrador.add(regresarButton, gbc);

        menuFrame.setContentPane(PAdministrador);
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
