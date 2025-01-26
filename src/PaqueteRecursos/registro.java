package PaqueteRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registro extends conexion {
    public JPanel PRegistro;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registrarmeButton;
    private JButton regresarAlInicioButton;
    private JCheckBox mostrarContraseniaCheckBox;

    public registro() {

        JFrame menuFrame = new JFrame("Crear Cuenta");
        menuFrame.setContentPane(PRegistro);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/cliente-nuevo.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);

        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreCliente = textField1.getText();
                String contraCliente = passwordField1.getText();
                String rolCliente;
                rolCliente = "cliente";

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

        regresarAlInicioButton.addActionListener(new ActionListener() {
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
