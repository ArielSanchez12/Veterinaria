package PaqueteRecursos;

import PaqueteAdministrador.administrador;
import PaqueteCliente.cliente;
import PaqueteSecretaria.secretaria;
import PaqueteVeterinario.veterinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends conexion {
    //public JPanel PLogin;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private JButton ingresarButton;
    private JButton regresarButton;
    private JCheckBox mostrarContraseniaCheckBox;

    public login() {
        JFrame menuFrame = new JFrame("Login");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menuFrame.setSize(500, 300);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/login.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa

        // Panel principal con fondo
        JPanel PLogin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/log-reg.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PLogin.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
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

        // Agregar componentes al panel
        gbc.gridx = 0; gbc.gridy = 0; PLogin.add(lblUsuario, gbc);
        gbc.gridx = 1; PLogin.add(textField1, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PLogin.add(lblContrasenia, gbc);
        gbc.gridx = 1; PLogin.add(passwordField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PLogin.add(mostrarContraseniaCheckBox, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PLogin.add(comboBox1, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PLogin.add(ingresarButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PLogin.add(regresarButton, gbc);

        menuFrame.setContentPane(PLogin);
        menuFrame.setVisible(true);

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
                                    menuFrame.dispose();
                                    new administrador();
                                }
                                case "cliente" -> {
                                    menuFrame.dispose();
                                    new cliente();
                                }
                                case "veterinario" -> {
                                    menuFrame.dispose();
                                    new veterinario();
                                }
                                case "secretaria" -> {
                                    menuFrame.dispose();
                                    new secretaria();
                                }
                                default -> JOptionPane.showMessageDialog(null, "No existe ese usuario en el rol actual, prueba con otro rol");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Asegurese de que el usuario pertenece al rol seleccionado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en clever cloud");
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new inicio();
            }
        });

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
