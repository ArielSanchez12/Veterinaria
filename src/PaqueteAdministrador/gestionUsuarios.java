package PaqueteAdministrador;

import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class gestionUsuarios extends conexion {
    private JTabbedPane tabbedPane1;
    public JPanel PGestion;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton agregarButton;
    private JCheckBox administradorCheckBox;
    private JCheckBox veterinarioCheckBox;
    private JCheckBox clienteCheckBox;
    private JCheckBox secretariaCheckBox;
    private JButton regresarButton;
    private JTextField textField2;
    private JButton actualizarButton;
    private JButton regresarButton1;
    private JButton verButton;
    private JButton regresarButton2;
    private JTextField textField4;
    private JButton eliminarButton;
    private JButton regresarButton3;
    private JCheckBox administradorCheckBox1;
    private JCheckBox veterinarioCheckBox1;
    private JCheckBox secretariaCheckBox1;
    private JCheckBox clienteCheckBox1;
    private JPasswordField passwordField2;

    public gestionUsuarios() {

        JFrame menuFrame = new JFrame("Gestion de Usuarios");
        menuFrame.setContentPane(PGestion);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.pack();
        menuFrame.setVisible(true);

        ButtonGroup grupoRolesCrear = new ButtonGroup();
        grupoRolesCrear.add(administradorCheckBox);
        grupoRolesCrear.add(clienteCheckBox);
        grupoRolesCrear.add(secretariaCheckBox);
        grupoRolesCrear.add(veterinarioCheckBox);

        ButtonGroup grupoRolesActualizar = new ButtonGroup();
        grupoRolesActualizar.add(administradorCheckBox1);
        grupoRolesActualizar.add(clienteCheckBox1);
        grupoRolesActualizar.add(secretariaCheckBox1);
        grupoRolesActualizar.add(veterinarioCheckBox1);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUser = textField1.getText();
                String contra = passwordField1.getText();
                String rol = "";

                if (administradorCheckBox.isSelected()) {
                    rol = "administrador";
                } else if (clienteCheckBox.isSelected()) {
                    rol = "cliente";
                } else if (secretariaCheckBox.isSelected()) {
                    rol = "secretaria";
                } else if (veterinarioCheckBox.isSelected()) {
                    rol = "veterinario";
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
                String actualizarUser = textField2.getText();
                String actualizarContra = passwordField2.getText();
                String rolActualizar = "";

                if (administradorCheckBox1.isSelected()) {
                    rolActualizar = "administrador";
                } else if (clienteCheckBox1.isSelected()) {
                    rolActualizar = "cliente";
                } else if (secretariaCheckBox1.isSelected()) {
                    rolActualizar = "secretaria";
                } else if (veterinarioCheckBox1.isSelected()) {
                    rolActualizar = "veterinario";
                }

                if (actualizarUser.isEmpty() || actualizarContra.isEmpty() || rolActualizar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                    return;
                }

                /* Aqui va el actualizar
                try (Connection conn = connect()) {
                    String sql = "INSERT INTO usuarios (usuario, contrasenia, rol) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, actualizarUser);
                    pstmt.setString(2, actualizarContra);
                    pstmt.setString(3, rolActualizar);
                    pstmt.execute();
                    JOptionPane.showMessageDialog(null, "Actualizacion de usuario exitoso");
                    textField2.setText("");
                    passwordField2.setText("");
                    grupoRolesActualizar.clearSelection();

                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }

                 */
            }
        });
    }
}
