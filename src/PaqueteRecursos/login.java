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
    public JPanel PLogin;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private JButton ingresarButton;
    private JButton regresarButton;
    private JCheckBox mostrarContraseniaCheckBox;

    public login() {

        JFrame menuFrame = new JFrame("Login");
        menuFrame.setContentPane(PLogin);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/login.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);
        comboBox1.addItem("administrador");
        comboBox1.addItem("cliente");
        comboBox1.addItem("veterinario");
        comboBox1.addItem("secretaria");


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
                    // Mostrar la contraseña como texto normal
                    passwordField1.setEchoChar((char) 0);
                } else {
                    // Ocultar la contraseña con los puntos predeterminados
                    passwordField1.setEchoChar('•');
                }
            }
        });
    }
}
