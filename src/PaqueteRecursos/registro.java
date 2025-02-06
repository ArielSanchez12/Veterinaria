package PaqueteRecursos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que representa la ventana de registro de nuevos usuarios en el sistema.
 * Permite la creación de cuentas para clientes.
 */
public class registro extends conexion {

    /** Campo de texto para ingresar el nombre de usuario. */
    private JTextField textField1;

    /** Campo de contraseña para ingresar la clave de acceso. */
    private JPasswordField passwordField1;

    /** Botón para confirmar el registro de un nuevo usuario. */
    private JButton registrarmeButton;

    /** Botón para regresar a la pantalla de inicio. */
    private JButton regresarAlInicioButton;

    /** Checkbox para mostrar u ocultar la contraseña ingresada. */
    private JCheckBox mostrarContraseniaCheckBox;

    /**
     * Constructor de la clase registro.
     * Configura la interfaz gráfica y los eventos de los componentes.
     */
    public registro() {
        JFrame frameRegistroClientesNuevos = new JFrame("Registro");
        frameRegistroClientesNuevos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRegistroClientesNuevos.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/nuevo.png")).getImage());
        frameRegistroClientesNuevos.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameRegistroClientesNuevos.setMinimumSize(new Dimension(800, 600));

        // Panel de fondo con imagen personalizada
        JPanel PRegistro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                InputStream imgStream = getClass().getClassLoader().getResourceAsStream("PaqueteRecursos/fondos/log-reg.jpeg");
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
        PRegistro.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 60, 22);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creación de componentes de la interfaz
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setOpaque(true);
        lblUsuario.setBackground(Color.BLACK);

        textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblContrasenia = new JLabel("Contraseña");
        lblContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
        lblContrasenia.setForeground(Color.WHITE);
        lblContrasenia.setOpaque(true);
        lblContrasenia.setBackground(Color.BLACK);

        passwordField1 = new JPasswordField(20);
        passwordField1.setFont(new Font("Arial", Font.PLAIN, 16));

        mostrarContraseniaCheckBox = new JCheckBox("Mostrar contraseña");
        mostrarContraseniaCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));

        registrarmeButton = new JButton("Registrarme");
        registrarmeButton.setFont(new Font("Arial", Font.BOLD, 18));
        registrarmeButton.setPreferredSize(new Dimension(200, 50));

        regresarAlInicioButton = new JButton("Regresar al Inicio");
        regresarAlInicioButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarAlInicioButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 0; gbc.gridy = 0; PRegistro.add(lblUsuario, gbc);
        gbc.gridx = 1; PRegistro.add(textField1, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PRegistro.add(lblContrasenia, gbc);
        gbc.gridx = 1; PRegistro.add(passwordField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PRegistro.add(mostrarContraseniaCheckBox, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PRegistro.add(registrarmeButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PRegistro.add(regresarAlInicioButton, gbc);

        frameRegistroClientesNuevos.setContentPane(PRegistro);
        frameRegistroClientesNuevos.setVisible(true);

        /**
         * Evento que maneja el registro de un nuevo usuario cuando se presiona el botón "Registrarme".
         * Inserta los datos en la base de datos y limpia los campos después del registro.
         */
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCliente = textField1.getText();
                String contraCliente = passwordField1.getText();
                String rolCliente = "cliente";

                if (nombreCliente.isEmpty() || contraCliente.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "INSERT INTO usuarios (usuario, contrasenia, rol) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, nombreCliente);
                    pstmt.setString(2, contraCliente);
                    pstmt.setString(3, rolCliente);
                    pstmt.execute();
                    JOptionPane.showMessageDialog(null, "Registro de usuario exitoso");
                    textField1.setText("");
                    passwordField1.setText("");
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        /**
         * Evento que permite regresar a la pantalla de inicio cuando se presiona el botón "Regresar al Inicio".
         */
        regresarAlInicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameRegistroClientesNuevos.dispose();
                new inicio();
            }
        });

        /**
         * Evento que permite mostrar u ocultar la contraseña ingresada cuando se interactúa con el checkbox.
         */
        mostrarContraseniaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrarContraseniaCheckBox.isSelected()) {
                    passwordField1.setEchoChar((char) 0);
                } else {
                    passwordField1.setEchoChar('•');
                }
            }
        });
    }
}