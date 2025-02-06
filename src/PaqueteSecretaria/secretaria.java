package PaqueteSecretaria;

import PaqueteRecursos.login;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que representa el menú principal de la secretaria de la veterinaria.
 * Permite a la secretaria generar y guardar en PDF los reportes de la empresa.
 * @author Ivan Castillo
 */

public class secretaria {
    private JButton crearReportesButton;
    private JButton cerrarSesiónButton;
    private JButton crearReporteDeCitasButton;

    public secretaria() {

        JFrame frameSecretaria = new JFrame("Menu Secretaria");
        frameSecretaria.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameSecretaria.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/secretaria.png")).getImage());
        frameSecretaria.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        frameSecretaria.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PSecretaria = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                InputStream imgStream = getClass().getClassLoader().getResourceAsStream("PaqueteRecursos/fondos/secre.jpeg");
                if (imgStream != null) {
                    try {
                        BufferedImage background = ImageIO.read(imgStream);
                        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("No se pudo cargar la imagen.");
                }
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
        crearReportesButton.setPreferredSize(new Dimension(275, 50));

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

        frameSecretaria.setContentPane(PSecretaria);
        frameSecretaria.setVisible(true);

        crearReportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new reporteFinanciero();
                frameSecretaria.dispose();
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameSecretaria.dispose();
                new login();
            }
        });
        crearReporteDeCitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameSecretaria.dispose();
                new reporteCita();
            }
        });
    }
}
