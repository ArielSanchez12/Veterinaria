package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class eliminar extends conexion {
    public JPanel PEliminarVet;
    public JButton eliminarButton;
    public JButton regresarButton;
    public JTextField textField1;

    public eliminar() {
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = textField1.getText().trim();  // Obtener el texto del campo de texto

                if (searchValue.isEmpty()) {
                    JOptionPane.showMessageDialog(PEliminarVet, "Por favor, ingrese un valor para buscar.");
                    return;
                }

                // Eliminar por código de cita o cédula
                try (Connection conn = connect()) {
                    // Usar OR en la consulta para buscar por código_cita o cédula
                    String sql = "DELETE FROM citas_veterinarias WHERE cedula = ? OR codigo_cita = ?";

                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, searchValue);  // Establecer el valor en la primera columna (cedula)
                    pstmt.setString(2, searchValue);  // Establecer el valor en la segunda columna (codigo_cita)

                    // Mostrar la consulta antes de ejecutarla para depuración
                    System.out.println("Ejecutando consulta: " + pstmt.toString());

                    int rowsAffected = pstmt.executeUpdate(); // Ejecutar la consulta

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(PEliminarVet, "Registro eliminado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(PEliminarVet, "No se encontró ningún registro con el valor ingresado.");
                    }

                } catch (Exception ex) {
                    // Mostrar detalles del error
                    JOptionPane.showMessageDialog(PEliminarVet, "Error al eliminar el registro: " + ex.getMessage());
                    ex.printStackTrace(); // Imprimir el error en la consola para depuración
                }
            }
        });

        regresarButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(PEliminarVet);
            if (parentFrame != null) {
                parentFrame.dispose(); // Cierra la ventana actual
            }
        });
    }
}
