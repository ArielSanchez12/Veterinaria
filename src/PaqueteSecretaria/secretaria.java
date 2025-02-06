package PaqueteSecretaria;

import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class secretaria {
    private JButton crearReportesButton;
    private JButton cerrarSesiónButton;
    private JButton crearReporteDeCitasButton;

    public secretaria() {

        JFrame menuFrame = new JFrame("Menu Secretaria");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/secretaria.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        menuFrame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PSecretaria = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/secre.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PSecretaria.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("MENU SECRETARIA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

        crearReportesButton = new JButton("Crear Reporte Financiero");
        crearReportesButton.setFont(new Font("Arial", Font.BOLD, 18));
        crearReportesButton.setPreferredSize(new Dimension(250, 50));

        crearReporteDeCitasButton = new JButton("Crear Reporte de Citas");
        crearReporteDeCitasButton.setFont(new Font("Arial", Font.BOLD, 18));
        crearReporteDeCitasButton.setPreferredSize(new Dimension(200, 50));

        cerrarSesiónButton = new JButton("Cerrar Sesion");
        cerrarSesiónButton.setFont(new Font("Arial", Font.BOLD, 18));
        cerrarSesiónButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 0; PSecretaria.add(titulo, gbc);
        gbc.gridx = 1; gbc.gridy = 1; PSecretaria.add(crearReportesButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PSecretaria.add(crearReporteDeCitasButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PSecretaria.add(cerrarSesiónButton, gbc);

        menuFrame.setContentPane(PSecretaria);
        menuFrame.setVisible(true);

        crearReportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new reporteFinanciero();
                menuFrame.dispose();
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });
        crearReporteDeCitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new reporteCita();
            }
        });
    }
}
