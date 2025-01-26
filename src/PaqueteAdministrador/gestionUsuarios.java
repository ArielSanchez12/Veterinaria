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

    public gestionUsuarios() {

        JFrame menuFrame = new JFrame("Gestion de Usuarios");
        menuFrame.setContentPane(PGestion);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/gestion.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);

        ButtonGroup grupoRolesCrear = new ButtonGroup();
        grupoRolesCrear.add(administradorCheckBox);
        grupoRolesCrear.add(secretariaCheckBox);
        grupoRolesCrear.add(veterinarioCheckBox);

        ButtonGroup grupoRolesActualizar = new ButtonGroup();
        grupoRolesActualizar.add(administradorCheckBox1);
        grupoRolesActualizar.add(secretariaCheckBox1);
        grupoRolesActualizar.add(veterinarioCheckBox1);

        ButtonGroup grupoRolesVer = new ButtonGroup();
        grupoRolesVer.add(administradorCheckBox2);
        grupoRolesVer.add(secretariaCheckBox2);
        grupoRolesVer.add(veterinarioCheckBox2);
        grupoRolesVer.add(clienteCheckBox2);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUser = textField1.getText();
                String contra = passwordField1.getText();
                String rol = "";

                if (administradorCheckBox.isSelected()) {
                    rol = "administrador";
                    nombreUser = "@Ad_" + nombreUser;
                } else if (secretariaCheckBox.isSelected()) {
                    rol = "secretaria";
                    nombreUser = "@Sec_" + nombreUser;
                } else if (veterinarioCheckBox.isSelected()) {
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
                    grupoRolesCrear.clearSelection();

                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUser = textField2.getText().trim();
                String nuevaContra = passwordField2.getText().trim();
                String nuevoRol = "";

                if (administradorCheckBox1.isSelected()) {
                    nuevoRol = "administrador";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Ad_");
                } else if (secretariaCheckBox1.isSelected()) {
                    nuevoRol = "secretaria";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Sec_");
                } else if (veterinarioCheckBox1.isSelected()) {
                    nuevoRol = "veterinario";
                    nombreUser = nombreUser.replaceFirst("^@\\w+_", "@Vet_");
                }

                // Verificación de campos obligatorios
                if (nombreUser.isEmpty() || nuevaContra.isEmpty() || nuevoRol.isEmpty()) {
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
                    grupoRolesActualizar.clearSelection();

                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + exception.getMessage());
                }
            }
        });
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String rolVer = "";
                if (administradorCheckBox2.isSelected()) {
                    rolVer = "administrador";
                } else if (secretariaCheckBox2.isSelected()) {
                    rolVer = "secretaria";
                } else if (veterinarioCheckBox2.isSelected()) {
                    rolVer = "veterinario";
                } else if (clienteCheckBox2.isSelected()) {
                    rolVer = "cliente";
                }

                if (rolVer.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un rol");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "SELECT usuario, contrasenia FROM usuarios WHERE rol = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, rolVer);
                    ResultSet rs = pstmt.executeQuery();
                    grupoRolesVer.clearSelection();


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

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioEliminar = textField3.getText();
                String rolEliminar = textField4.getText();

                if (usuarioEliminar.isEmpty() || rolEliminar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un usuario y un rol");
                    return;
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
                            textField4.setText("");
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
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new administrador();
            }
        });

        regresarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new administrador();
            }
        });

        regresarButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new administrador();
            }
        });

        regresarButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new administrador();
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
    }
}
