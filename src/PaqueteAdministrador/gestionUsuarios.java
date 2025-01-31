package PaqueteAdministrador;
import PaqueteRecursos.conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class gestionUsuarios extends conexion {
    public JTabbedPane tabbedPane1;
    public JPanel PGestion;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton agregarButton;
    private JCheckBox administradorCheckBox;
    private JCheckBox veterinarioCheckBox;
    private JCheckBox secretariaCheckBox;
    private JButton regresarButton;
    private JTextField textField2;
    private JButton actualizarButton;
    private JButton regresarButton1;
    private JButton verUsuariosButton;
    private JButton regresarButton4;
    private JTextField textField4;
    private JButton eliminarButton;
    private JButton regresarButton3;
    private JCheckBox administradorCheckBox1;
    private JCheckBox veterinarioCheckBox1;
    private JCheckBox secretariaCheckBox1;
    private JPasswordField passwordField2;
    private JCheckBox administradorCheckBox2;
    private JCheckBox secretariaCheckBox2;
    private JCheckBox veterinarioCheckBox2;
    public JScrollPane JScrollPaneVer;
    private JCheckBox clienteCheckBox2;
    private JTextField textField3;
    private JCheckBox mostrarContraseniaCheckBox1;
    private JCheckBox mostrarContraseniaCheckBox;
    private JComboBox comboBox1;
    private JLabel backgroundLabel;

    public gestionUsuarios() {
        JFrame menuFrame = new JFrame("Gestion de Usuarios");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/gestion.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa

        // Cargar imagen de fondo
        ImageIcon background = new ImageIcon("src/PaqueteRecursos/fondos/admin.png");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setLayout(new BorderLayout());

        tabbedPane1 = new JTabbedPane();

        PGestion = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/admin.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        agregarComponentes();
        tabbedPane1.addTab("Agregar", PGestion);
        backgroundLabel.add(tabbedPane1, BorderLayout.CENTER);

        menuFrame.setContentPane(backgroundLabel);
        menuFrame.setVisible(true);
    }

    private void agregarComponentes() {
        PGestion.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Agregar Usuarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 0, 0));

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 18));

        textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaLabel.setFont(new Font("Arial", Font.BOLD, 18));

        passwordField1 = new JPasswordField(20);
        passwordField1.setFont(new Font("Arial", Font.PLAIN, 16));


        mostrarContraseniaCheckBox = new JCheckBox("Mostrar contraseña");
        mostrarContraseniaCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));

        comboBox1 = new JComboBox<>(new String[]{"administrador", "veterinario", "secretaria"});
        comboBox1.setFont(new Font("Arial", Font.PLAIN, 16));

        agregarButton = new JButton("Agregar");
        agregarButton.setFont(new Font("Arial", Font.BOLD, 18));
        agregarButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Regresar");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));
        //PGestion.add(regresarButton);

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 0; PGestion.add(titulo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PGestion.add(usuarioLabel, gbc);
        gbc.gridx = 1; PGestion.add(textField1, gbc);
        gbc.gridx = 0; gbc.gridy = 2; PGestion.add(contrasenaLabel, gbc);
        gbc.gridx = 1; PGestion.add(passwordField1, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PGestion.add(mostrarContraseniaCheckBox, gbc);
        gbc.gridx = 1; gbc.gridy = 4; PGestion.add(comboBox1, gbc);
        gbc.gridx = 1; gbc.gridy = 5; PGestion.add(agregarButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6; PGestion.add(regresarButton, gbc);
        agregarEventos();
    }

    private void agregarEventos() {
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUser = textField1.getText();
                String contra = passwordField1.getText();
                String rol = (String) comboBox1.getSelectedItem();

                if ("Administrador".equals(rol)) {
                    rol = "administrador";
                    nombreUser = "@Ad_" + nombreUser;
                } else if ("Secretaria".equals(rol)) {
                    rol = "secretaria";
                    nombreUser = "@Sec_" + nombreUser;
                } else if ("Veterinario".equals(rol)) {
                    rol = "veterinario";
                    nombreUser = "@Vet_" + nombreUser;
                }

                if (nombreUser.isEmpty() || contra.isEmpty() || rol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "INSERT INTO usuarios (usuario, contrasenia, rol) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, nombreUser);
                    pstmt.setString(2, contra);
                    pstmt.setString(3, rol);
                    pstmt.execute();
                    JOptionPane.showMessageDialog(null, "Registro de usuario exitoso");
                    textField1.setText("");
                    passwordField1.setText("");

                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
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

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.getWindowAncestor(PGestion)).dispose();
                new administrador();
            }
        });
    }
}
