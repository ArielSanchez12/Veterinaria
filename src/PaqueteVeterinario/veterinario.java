package PaqueteVeterinario;

import PaqueteRecursos.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class veterinario {
    public JButton gestiónDeCitasButton;
    public JButton historialMédicoDeMascotasButton;
    public JButton eliminarCitaYTrataButton;
    public JButton salirButton;
    public JButton diagnosticosYTratamientosButton;


    public veterinario() {
        JFrame menuFrame = new JFrame("Menu Veterinario");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/veterinario.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        menuFrame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PVeterinario = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/vet.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PVeterinario.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes

        JLabel fakeTitulo1 = new JLabel("", SwingConstants.CENTER);
        fakeTitulo1.setFont(new Font("Arial", Font.BOLD, 24));
        fakeTitulo1.setForeground(new Color(0, 0, 0));

        JLabel fakeTitulo2 = new JLabel("", SwingConstants.CENTER);
        fakeTitulo2.setFont(new Font("Arial", Font.BOLD, 24));
        fakeTitulo2.setForeground(new Color(0, 0, 0));

        gestiónDeCitasButton = new JButton("Gestion de Citas");
        gestiónDeCitasButton.setFont(new Font("Arial", Font.BOLD, 18));
        gestiónDeCitasButton.setPreferredSize(new Dimension(300, 50));

        eliminarCitaYTrataButton = new JButton("Eliminar Citas");
        eliminarCitaYTrataButton.setFont(new Font("Arial", Font.BOLD, 18));
        eliminarCitaYTrataButton.setPreferredSize(new Dimension(200, 50));

        historialMédicoDeMascotasButton = new JButton("Historial Médico de Mascotas");
        historialMédicoDeMascotasButton.setFont(new Font("Arial", Font.BOLD, 18));
        historialMédicoDeMascotasButton.setPreferredSize(new Dimension(200, 50));

        diagnosticosYTratamientosButton = new JButton("Diagnosticos y Tratamientos");
        diagnosticosYTratamientosButton.setFont(new Font("Arial", Font.BOLD, 18));
        diagnosticosYTratamientosButton.setPreferredSize(new Dimension(200, 50));

        salirButton = new JButton("Cerrar Sesion");
        salirButton.setFont(new Font("Arial", Font.BOLD, 18));
        salirButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 1; PVeterinario.add(fakeTitulo1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PVeterinario.add(fakeTitulo2, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PVeterinario.add(gestiónDeCitasButton, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PVeterinario.add(eliminarCitaYTrataButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PVeterinario.add(historialMédicoDeMascotasButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6; PVeterinario.add(diagnosticosYTratamientosButton, gbc);
        gbc.gridx = 1; gbc.gridy = 7; PVeterinario.add(salirButton, gbc);

        menuFrame.setContentPane(PVeterinario);
        menuFrame.setVisible(true);

        gestiónDeCitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                citas ventanaCitas = new citas();
                ventanaCitas.setVisible(true);
            }
        });


        historialMédicoDeMascotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new historial();
            }
        });

        eliminarCitaYTrataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new eliminar();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });

        diagnosticosYTratamientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new DiagnosticoyTratamiento();
            }
        });
    }
}
