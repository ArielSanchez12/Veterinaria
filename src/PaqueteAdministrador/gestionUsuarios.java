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
    private JButton eliminarButton;
    private JButton regresarButton3;
    private JPasswordField passwordField2;
    public JScrollPane JScrollPaneVer;
    private JTextField textField3;
    private JCheckBox mostrarContraseniaCheckBox1;
    private JCheckBox mostrarContraseniaCheckBox;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JPanel PActualizar;
    private JComboBox comboBox3;
    private JPanel PVer;
    private JPanel PEliminar;
    private JComboBox comboBox4;
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

        PActualizar = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/admin.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        PVer = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/admin.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        PEliminar = new JPanel(null) {
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
        agregarComponentes2();
        tabbedPane1.addTab("Actualizar", PActualizar);
        backgroundLabel.add(tabbedPane1, BorderLayout.CENTER);
        agregarComponentes3();
        tabbedPane1.addTab("Ver", PVer);
        backgroundLabel.add(tabbedPane1, BorderLayout.CENTER);
        agregarComponentes4();
        tabbedPane1.addTab("Eliminar", PEliminar);
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

    //Actualizar
    private void agregarComponentes2() {
        PActualizar.setLayout(new GridBagLayout());

        GridBagConstraints act = new GridBagConstraints();
        act.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        act.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo2 = new JLabel("Actualizar Usuarios", SwingConstants.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 24));
        titulo2.setForeground(new Color(0, 0, 0));

        JLabel usuarioLabel2 = new JLabel("Usuario a Actualizar:");
        usuarioLabel2.setFont(new Font("Arial", Font.BOLD, 18));

        textField2 = new JTextField(20);
        textField2.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel contrasenaLabel2 = new JLabel("Nueva Contraseña:");
        contrasenaLabel2.setFont(new Font("Arial", Font.BOLD, 18));

        passwordField2 = new JPasswordField(20);
        passwordField2.setFont(new Font("Arial", Font.PLAIN, 16));


        mostrarContraseniaCheckBox1 = new JCheckBox("Mostrar contraseña");
        mostrarContraseniaCheckBox1.setFont(new Font("Arial", Font.PLAIN, 16));

        comboBox2 = new JComboBox<>(new String[]{"administrador", "veterinario", "secretaria"});
        comboBox2.setFont(new Font("Arial", Font.PLAIN, 16));

        actualizarButton = new JButton("Actualizar");
        actualizarButton.setFont(new Font("Arial", Font.BOLD, 18));
        actualizarButton.setPreferredSize(new Dimension(200, 50));

        regresarButton1 = new JButton("Regresar");
        regresarButton1.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton1.setPreferredSize(new Dimension(200, 50));


        // Agregar componentes al panel
        act.gridx = 1; act.gridy = 0; PActualizar.add(titulo2, act);
        act.gridx = 0; act.gridy = 1; PActualizar.add(usuarioLabel2, act);
        act.gridx = 1; PActualizar.add(textField2, act);
        act.gridx = 0; act.gridy = 2; PActualizar.add(contrasenaLabel2, act);
        act.gridx = 1; PActualizar.add(passwordField2, act);
        act.gridx = 1; act.gridy = 3; PActualizar.add(mostrarContraseniaCheckBox1, act);
        act.gridx = 1; act.gridy = 4; PActualizar.add(comboBox2, act);
        act.gridx = 1; act.gridy = 5; PActualizar.add(actualizarButton, act);
        act.gridx = 1; act.gridy = 6; PActualizar.add(regresarButton1, act);
        agregarEventos2();
    }

    private void agregarEventos2() {
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUser = textField2.getText().trim();
                String nuevaContra = passwordField2.getText().trim();
                String rol2 = (String) comboBox2.getSelectedItem();
                String nuevoRol = "";

                if ("administrador".equals(rol2)) {
                    nuevoRol = "administrador";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Ad_");
                } else if ("secretaria".equals(rol2)) {
                    nuevoRol = "secretaria";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Sec_");
                } else if ("veterinario".equals(rol2)) {
                    nuevoRol = "veterinario";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Vet_");
                }

                // Verificación de campos obligatorios
                if (nombreUser.isEmpty() || nuevaContra.isEmpty() || rol2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos correctamente.");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "UPDATE usuarios SET usuario = ?, contrasenia = ?, rol = ? WHERE usuario = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, nombreUser);
                    pstmt.setString(2, nuevaContra);
                    pstmt.setString(3, nuevoRol);
                    pstmt.setString(4, textField2.getText().trim()); // Nombre de usuario original antes de editar

                    int filasAfectadas = pstmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el usuario a actualizar.");
                    }

                    // Limpiar los campos después de la actualización
                    textField2.setText("");
                    passwordField2.setText("");

                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + exception.getMessage());
                }
            }
        });

        mostrarContraseniaCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrarContraseniaCheckBox1.isSelected()) {
                    passwordField2.setEchoChar((char) 0);
                } else {
                    passwordField2.setEchoChar('•');
                }
            }
        });

        regresarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.getWindowAncestor(PGestion)).dispose();
                new administrador();
            }
        });
    }

    //Ver
    private void agregarComponentes3() {
        PVer.setLayout(new GridBagLayout());

        GridBagConstraints ver = new GridBagConstraints();
        ver.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        ver.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo3 = new JLabel("Ver Usuarios registrados segun su Rol", SwingConstants.CENTER);
        titulo3.setFont(new Font("Arial", Font.BOLD, 24));
        titulo3.setForeground(new Color(0, 0, 0));

        comboBox3 = new JComboBox<>(new String[]{"administrador", "veterinario", "secretaria", "cliente"});
        comboBox3.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPaneVer = new JScrollPane();
        JScrollPaneVer.setPreferredSize(new Dimension(400, 200));

        verUsuariosButton = new JButton("Ver Usuarios");
        verUsuariosButton.setFont(new Font("Arial", Font.BOLD, 18));
        verUsuariosButton.setPreferredSize(new Dimension(200, 50));

        regresarButton4 = new JButton("Regresar");
        regresarButton4.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton4.setPreferredSize(new Dimension(200, 50));


        // Agregar componentes al panel
        ver.gridx = 1; ver.gridy = 0; PVer.add(titulo3, ver);
        ver.gridx = 1; ver.gridy = 1; PVer.add(comboBox3, ver);
        ver.gridx = 1; ver.gridy = 2; PVer.add(JScrollPaneVer, ver);
        ver.gridx = 1; ver.gridy = 3; PVer.add(verUsuariosButton, ver);
        ver.gridx = 1; ver.gridy = 4; PVer.add(regresarButton4, ver);
        agregarEventos3();
    }

    private void agregarEventos3() {
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rol3 = (String) comboBox3.getSelectedItem();
                String rolVer = "";

                if ("administrador".equals(rol3)) {
                    rolVer = "administrador";
                } else if ("secretaria".equals(rol3)) {
                    rolVer = "secretaria";
                } else if ("veterinario".equals(rol3)) {
                    rolVer = "veterinario";
                } else if ("cliente".equals(rol3)) {
                    rolVer = "cliente";
                }

                if (rol3.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un rol");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "SELECT usuario, contrasenia FROM usuarios WHERE rol = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, rolVer);
                    ResultSet rs = pstmt.executeQuery();


                    String[] columnas = {"Usuario", "Contraseña"};
                    DefaultTableModel modeloTablaVer = new DefaultTableModel(null, columnas) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    while (rs.next()) {
                        String nombreUserVer = rs.getString("usuario");
                        String contraseniaUserVer = rs.getString("contrasenia");


                        modeloTablaVer.addRow(new Object[]{nombreUserVer, contraseniaUserVer});
                    }

                    JTable tablaVer = new JTable(modeloTablaVer);

                    tablaVer.setRowHeight(30);
                    tablaVer.setFillsViewportHeight(true);


                    JScrollPane scrollTablaVer = new JScrollPane(tablaVer);
                    JScrollPaneVer.setViewportView(scrollTablaVer);

                } catch (SQLException x) {
                    x.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cargar los usuarios");
                }
            }
        });


        regresarButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.getWindowAncestor(PGestion)).dispose();
                new administrador();
            }
        });
    }

    //Eliminar
    private void agregarComponentes4() {
        PEliminar.setLayout(new GridBagLayout());

        GridBagConstraints elim = new GridBagConstraints();
        elim.insets = new Insets(15, -15, 35, 15); // Aumentar espacio entre componentes
        elim.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo4 = new JLabel("Eliminar Usuario de la Base de Datos", SwingConstants.CENTER);
        titulo4.setFont(new Font("Arial", Font.BOLD, 24));
        titulo4.setForeground(new Color(0, 0, 0));

        JLabel usuarioLabel3 = new JLabel("Usuario a Eliminar:");
        usuarioLabel3.setFont(new Font("Arial", Font.BOLD, 18));

        textField3 = new JTextField(20);
        textField3.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel rolEliminarLabel = new JLabel("Rol:");
        rolEliminarLabel.setFont(new Font("Arial", Font.BOLD, 18));

        comboBox4 = new JComboBox<>(new String[]{"administrador", "veterinario", "secretaria", "cliente"});
        comboBox4.setFont(new Font("Arial", Font.PLAIN, 16));

        eliminarButton = new JButton("Eliminar Usuario");
        eliminarButton.setFont(new Font("Arial", Font.BOLD, 18));
        eliminarButton.setPreferredSize(new Dimension(200, 50));

        regresarButton3 = new JButton("Regresar");
        regresarButton3.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton3.setPreferredSize(new Dimension(200, 50));


        // Agregar componentes al panel
        elim.gridx = 1; elim.gridy = 0; PEliminar.add(titulo4, elim);
        elim.gridx = 0; elim.gridy = 1; PEliminar.add(usuarioLabel3, elim);
        elim.gridx = 1; elim.gridy = 1; PEliminar.add(textField3, elim);
        elim.gridx = 0; elim.gridy = 2; PEliminar.add(rolEliminarLabel, elim);
        elim.gridx = 1; elim.gridy = 2; PEliminar.add(comboBox4, elim);
        elim.gridx = 1; elim.gridy = 3; PEliminar.add(eliminarButton, elim);
        elim.gridx = 1; elim.gridy = 4; PEliminar.add(regresarButton3, elim);
        agregarEventos4();
    }

    private void agregarEventos4() {
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioEliminar = textField3.getText();
                String selectEliminar = (String) comboBox4.getSelectedItem();
                String rolEliminar = "";

                if (usuarioEliminar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un usuario y un rol");
                    return;
                }

                if ("administrador".equals(selectEliminar)) {
                    rolEliminar = "administrador";
                } else if ("secretaria".equals(selectEliminar)) {
                    rolEliminar = "secretaria";
                } else if ("veterinario".equals(selectEliminar)) {
                    rolEliminar = "veterinario";
                } else if ("cliente".equals(selectEliminar)) {
                    rolEliminar = "cliente";
                }

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que deseas eliminar el usuario \"" + usuarioEliminar +
                                "\" para siempre?\n(Para siempre es muuucho tiempo)",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try (Connection conn = connect()) {
                        String sql = "DELETE FROM usuarios WHERE usuario = ? AND rol = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, usuarioEliminar);
                        pstmt.setString(2, rolEliminar);

                        int filasAfectadas = pstmt.executeUpdate();

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente");
                            textField3.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario o rol incorrecto");
                        }

                    } catch (SQLException x) {
                        x.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al eliminar el usuario");
                    }
                }
            }
        });

        regresarButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) SwingUtilities.getWindowAncestor(PGestion)).dispose();
                new administrador();
            }
        });
    }
}
