package PaqueteRecursos;

import PaqueteAdministrador.administrador;
import PaqueteCliente.cliente;
import PaqueteSecretaria.secretaria;
import PaqueteVeterinario.veterinario;

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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa la ventana de inicio de sesión para los usuarios del sistema.
 * Permite a los usuarios autenticarse y acceder según su rol asignado.
 */
public class login extends conexion {
    /** Campo de texto para ingresar el nombre de usuario. */
    private JTextField textField1;

    /** Campo de contraseña para ingresar la clave de acceso. */
    private JPasswordField passwordField1;

    /** ComboBox para seleccionar el rol del usuario (Administrador, Cliente, Veterinario o Secretaria). */
    private JComboBox<String> comboBox1;

    /** Botón para confirmar el inicio de sesión. */
    private JButton ingresarButton;

    /** Botón para regresar a la pantalla de inicio. */
    private JButton regresarButton;

    /** Checkbox para mostrar u ocultar la contraseña ingresada. */
    private JCheckBox mostrarContraseniaCheckBox;

    /**
     * Constructor de la clase login.
     * Configura la interfaz gráfica y los eventos de los componentes.
     */
    public login() {
        JFrame frameLogin = new JFrame("Login");
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/login.png")).getImage());
        frameLogin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameLogin.setMinimumSize(new Dimension(800, 600));

        // Panel de fondo con imagen personalizada
        JPanel PLogin = new JPanel() {
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
        PLogin.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 22);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creación de componentes de la interfaz
        JLabel lblUsuario = new JLabel("Usuario", SwingConstants.CENTER);
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

        comboBox1 = new JComboBox<>(new String[]{"administrador", "cliente", "veterinario", "secretaria"});
        comboBox1.setFont(new Font("Arial", Font.PLAIN, 16));

        ingresarButton = new JButton("Iniciar Sesion");
        ingresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        ingresarButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Regresar al Inicio");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Posicionamiento de los componentes en el panel
        gbc.gridx = 0; gbc.gridy = 0; PLogin.add(lblUsuario, gbc);
        gbc.gridx = 1; PLogin.add(textField1, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PLogin.add(lblContrasenia, gbc);
        gbc.gridx = 1; PLogin.add(passwordField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PLogin.add(mostrarContraseniaCheckBox, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PLogin.add(comboBox1, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PLogin.add(ingresarButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PLogin.add(regresarButton, gbc);

        frameLogin.setContentPane(PLogin);
        frameLogin.setVisible(true);

        /**
         * Evento que maneja el inicio de sesión cuando se presiona el botón "Iniciar Sesión".
         * Valida los datos ingresados y permite el acceso según el rol seleccionado.
         */
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText();
                String contrasenia = new String(passwordField1.getPassword());
                String rolSeleccionado = (String) comboBox1.getSelectedItem();

                if (usuario.isEmpty() || contrasenia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "SELECT rol FROM usuarios WHERE usuario = ? AND contrasenia = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, usuario);
                    pstmt.setString(2, contrasenia);

                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                        String rol = resultSet.getString("rol");

                        if (rol.equals(rolSeleccionado)) {
                            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso! Bienvenido " + usuario);

                            switch (rol) {
                                case "administrador" -> {
                                    frameLogin.dispose();
                                    new administrador();
                                }
                                case "cliente" -> {
                                    frameLogin.dispose();
                                    new cliente();
                                }
                                case "veterinario" -> {
                                    frameLogin.dispose();
                                    new veterinario();
                                }
                                case "secretaria" -> {
                                    frameLogin.dispose();
                                    new secretaria();
                                }
                                default -> JOptionPane.showMessageDialog(null, "No existe ese usuario en el rol actual, prueba con otro rol");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Asegúrese de que el usuario pertenece al rol seleccionado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en la base de datos");
                }
            }
        });

        /**
         * Evento que permite regresar a la pantalla de inicio cuando se presiona el botón "Regresar al Inicio".
         */
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameLogin.dispose();
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