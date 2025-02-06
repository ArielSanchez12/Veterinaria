package PaqueteRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registro extends conexion {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registrarmeButton;
    private JButton regresarAlInicioButton;
    private JCheckBox mostrarContraseniaCheckBox;

    public registro() {

        JFrame menuFrame = new JFrame("Registro");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/nuevo.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        menuFrame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PRegistro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/log-reg.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PRegistro.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 60, 22); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsuario.setForeground(new Color(255, 255, 255));
        lblUsuario.setOpaque(true);
        lblUsuario.setBackground(Color.BLACK);

        textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblContrasenia = new JLabel("Contraseña");
        lblContrasenia.setFont(new Font("Arial", Font.BOLD, 18));
        lblContrasenia.setForeground(new Color(255, 255, 255));
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

        menuFrame.setContentPane(PRegistro);
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
