package PaqueteCliente;

import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class cliente {
    public JButton agendarCitaButton;
    public JButton historialMédicoDeMiButton;
    public JButton salirButton;
    public JButton eliminarRegistroButton;

    public cliente() {

        JFrame frame = new JFrame("Menu Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/cliente.png").getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PCliente = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/cliente.jpeg");
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PCliente.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        agendarCitaButton = new JButton("Agendar Cita Medica");
        agendarCitaButton.setFont(new Font("Arial", Font.BOLD, 18));
        agendarCitaButton.setPreferredSize(new Dimension(250, 50));

        historialMédicoDeMiButton = new JButton("Historial Medico");
        historialMédicoDeMiButton.setFont(new Font("Arial", Font.BOLD, 18));
        historialMédicoDeMiButton.setPreferredSize(new Dimension(200, 50));

        eliminarRegistroButton = new JButton("Eliminar Registro");
        eliminarRegistroButton.setFont(new Font("Arial", Font.BOLD, 18));
        eliminarRegistroButton.setPreferredSize(new Dimension(200, 50));

        salirButton = new JButton("Salir");
        salirButton.setFont(new Font("Arial", Font.BOLD, 18));
        salirButton.setPreferredSize(new Dimension(200, 50));



        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 1; PCliente.add(agendarCitaButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PCliente.add(eliminarRegistroButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PCliente.add(historialMédicoDeMiButton, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PCliente.add(salirButton, gbc);

        frame.setContentPane(PCliente);
        frame.setVisible(true);


        agendarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarMascota();
                frame.dispose();
            }
        });

        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarRegistro();
                frame.dispose();
            }
        });


        historialMédicoDeMiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historialMedico();
                frame.dispose();
            }
        });


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new login();
            }
        });
    }
}