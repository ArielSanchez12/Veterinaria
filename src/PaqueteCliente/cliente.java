/**
 * Clase que representa el menú principal para los clientes en el sistema.
 * Permite a los clientes agendar citas, ver su historial médico, eliminar su registro y salir de la sesión.
 */
package PaqueteCliente;

import PaqueteRecursos.login;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class cliente {
    /** Botón para agendar una cita médica. */
    public JButton agendarCitaButton;
    /** Botón para acceder al historial médico del cliente. */
    public JButton historialMédicoDeMiButton;
    /** Botón para salir del sistema y volver al login. */
    public JButton salirButton;
    /** Botón para eliminar el registro del cliente en el sistema. */
    public JButton eliminarRegistroButton;

    /**
     * Constructor de la clase cliente.
     * Configura la interfaz gráfica y los eventos de los botones.
     */
    public cliente() {
        JFrame frameCliente = new JFrame("Menu Cliente");
        frameCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCliente.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/cliente.png").getImage());
        frameCliente.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameCliente.setMinimumSize(new Dimension(800, 600));

        JPanel PCliente = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/cliente.jpeg");
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PCliente.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15);
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

        frameCliente.setContentPane(PCliente);
        frameCliente.setVisible(true);

        /**
         * Acción que permite abrir la ventana para registrar una mascota al agendar una cita.
         */
        agendarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarMascota();
                frameCliente.dispose();
            }
        });

        /**
         * Acción que permite eliminar el registro del cliente en el sistema.
         */
        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarRegistro();
                frameCliente.dispose();
            }
        });

        /**
         * Acción que permite ver el historial médico del cliente.
         */
        historialMédicoDeMiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historialMedico();
                frameCliente.dispose();
            }
        });

        /**
         * Acción que permite salir del sistema y regresar a la pantalla de login.
         */
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameCliente.dispose();
                new login();
            }
        });
    }
}