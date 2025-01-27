package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eliminar extends conexion {
    public JPanel PEliminar;
    public JTextField textFieldMascotaID;
    public JButton eliminarButton;
    public JButton regresarButton;
    private JTextField textField1;

    public eliminar() {
        JFrame frame = new JFrame("Eliminar Historial");
        frame.setContentPane(PEliminar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        // Configurar botón para eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mascotaID = textFieldMascotaID.getText().trim();

                if (mascotaID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
                    return;
                }

                // Confirmar acción
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el historial de la mascota con ID: " + mascotaID + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarHistorial(mascotaID);
                }
            }
        });

        // Configurar botón para regresar
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new historial(); // Volver al historial
            }
        });
    }

    private void eliminarHistorial(String mascotaID) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM HistorialMascotas WHERE mascota_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mascotaID);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Historial eliminado con éxito.");
                textFieldMascotaID.setText(""); // Limpiar campo
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un historial con el ID proporcionado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el historial.");
        }
    }
}
