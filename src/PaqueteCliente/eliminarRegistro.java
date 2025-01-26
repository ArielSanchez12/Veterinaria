package PaqueteCliente;

import PaqueteAdministrador.administrador;
import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eliminarRegistro extends conexion {
    public JPanel PEliminarR;
    public JTextField textField1;
    public JButton eliminarRegistroButton;
    public JButton regresarButton;

    public eliminarRegistro() {

        JFrame frame = new JFrame("Eliminar Registro");
        frame.setContentPane(PEliminarR);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/eliminar-registro.png").getImage());
        frame.pack();
        frame.setVisible(true);

        // Configurar botón para eliminar registro
        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaRegistro = textField1.getText().trim();

                if (cedulaRegistro.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una cedula válido.");
                    return;
                }

                // Confirmar acción
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el registro con la cedula: " + cedulaRegistro + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarRegistro(cedulaRegistro);
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new cliente();
            }
        });
    }

    private void eliminarRegistro(String idRegistro) {
        try (Connection conn = connect()) {

            String sql = "DELETE FROM agendar_citas WHERE cedula = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idRegistro);


            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado con exito.");
                textField1.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro un registro con la cedula proporcionado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro.");
        }
    }
}
