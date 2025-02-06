package PaqueteAdministrador;

import PaqueteRecursos.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica del menú de administrador.
 * Permite al usuario acceder a la gestión de usuarios, estadísticas y cerrar sesión.
 */
public class administrador {

    // Botones de la interfaz
    private JButton gestionDeUsuariosButton;
    private JButton estadisticasButton;
    private JButton regresarButton;

    /**
     * Constructor de la clase administrador.
     * Inicializa la interfaz gráfica del menú de administrador, configurando la ventana,
     * los botones y sus acciones correspondientes.
     */
    public administrador() {

        // Configuración de la ventana principal
        JFrame frameAdministrador = new JFrame("Menu Administrador");
        frameAdministrador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdministrador.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/administrador.png").getImage());
        frameAdministrador.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        frameAdministrador.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo personalizado
        JPanel PAdministrador = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/admin.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PAdministrador.setLayout(new GridBagLayout()); // Diseño responsivo

        // Configuración del diseño de los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón para la gestión de usuarios
        gestionDeUsuariosButton = new JButton("Gestion de Usuarios");
        gestionDeUsuariosButton.setFont(new Font("Arial", Font.BOLD, 18));
        gestionDeUsuariosButton.setPreferredSize(new Dimension(250, 50));

        // Botón para las estadísticas
        estadisticasButton = new JButton("Estadisticas");
        estadisticasButton.setFont(new Font("Arial", Font.BOLD, 18));
        estadisticasButton.setPreferredSize(new Dimension(200, 50));

        // Botón para cerrar sesión
        regresarButton = new JButton("Cerrar Sesion");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 1; PAdministrador.add(gestionDeUsuariosButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PAdministrador.add(estadisticasButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PAdministrador.add(regresarButton, gbc);

        // Configurar el panel principal en la ventana
        frameAdministrador.setContentPane(PAdministrador);
        frameAdministrador.setVisible(true);

        // Acción del botón de gestión de usuarios
        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameAdministrador.dispose(); // Cierra la ventana actual
                new gestionUsuarios(); // Abre la ventana de gestión de usuarios
            }
        });

        // Acción del botón de estadísticas
        estadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameAdministrador.dispose(); // Cierra la ventana actual
                new estadisticas(); // Abre la ventana de estadísticas
            }
        });

        // Acción del botón de cerrar sesión
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameAdministrador.dispose(); // Cierra la ventana actual
                new login(); // Abre la ventana de inicio de sesión
            }
        });
    }
}