package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiagnosticoyTratamiento extends conexion {
    public JPanel PDiag;
    public JTabbedPane tabbedPane1;
    public JButton regresarButton;
    public JButton guardarButton;
    public JTextField textField1;  // Código de la cita
    public JTextArea textArea1;    // Diagnóstico
    public JTextArea textArea2;    // Tratamiento
    public JButton buscarButton;
    public JLabel MostrarCita;

    public DiagnosticoyTratamiento() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCitaPorCodigo();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDiagnostico();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(PDiag);
                if (parentFrame != null) {
                    parentFrame.dispose(); // Cierra la ventana actual
                }
            }
        });
    }

    private void buscarCitaPorCodigo() {
        String codigoCita = textField1.getText().trim();
        if (codigoCita.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un código de cita.");
            return;
        }
        if (!codigoCita.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El código de cita debe ser un número.");
            return;
        }

        String sql = "SELECT c.codigo_cita, c.cedula, c.fecha, c.hora, c.tipo_mascota, c.nombre_mascota, " +
                "c.sexo_mascota, c.tipo_servicio, c.motivo_cita, c.costo, " +
                "d.diagnostico, d.tratamiento " +
                "FROM citas_veterinarias c " +
                "LEFT JOIN diagnosticos_tratamientos d ON c.codigo_cita = d.codigo_cita " +
                "WHERE c.codigo_cita = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(codigoCita));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                StringBuilder resultado = new StringBuilder("<html><table border='1' cellspacing='0' cellpadding='5'>");
                resultado.append("<tr><th>Código Cita</th><th>Cédula</th><th>Fecha</th><th>Hora</th><th>Tipo Mascota</th><th>Nombre Mascota</th><th>Sexo</th><th>Servicio</th><th>Motivo</th><th>Costo</th><th>Diagnóstico</th><th>Tratamiento</th></tr>");

                resultado.append("<tr>")
                        .append("<td>").append(rs.getInt("codigo_cita")).append("</td>")
                        .append("<td>").append(rs.getString("cedula")).append("</td>")
                        .append("<td>").append(rs.getDate("fecha")).append("</td>")
                        .append("<td>").append(rs.getTime("hora")).append("</td>")
                        .append("<td>").append(rs.getString("tipo_mascota")).append("</td>")
                        .append("<td>").append(rs.getString("nombre_mascota")).append("</td>")
                        .append("<td>").append(rs.getString("sexo_mascota")).append("</td>")
                        .append("<td>").append(rs.getString("tipo_servicio")).append("</td>")
                        .append("<td>").append(rs.getString("motivo_cita")).append("</td>")
                        .append("<td>").append(rs.getDouble("costo")).append("</td>")
                        .append("<td>").append(rs.getString("diagnostico") == null ? "Sin diagnóstico" : rs.getString("diagnostico")).append("</td>")
                        .append("<td>").append(rs.getString("tratamiento") == null ? "Sin tratamiento" : rs.getString("tratamiento")).append("</td>")
                        .append("</tr>");

                resultado.append("</table></html>");

                MostrarCita.setText(resultado.toString());

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una cita con ese código.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al recuperar los datos de la cita.");
        }
    }

    private void guardarDiagnostico() {
        String codigoCita = textField1.getText().trim();
        String diagnostico = textArea1.getText().trim();
        String tratamiento = textArea2.getText().trim();

        if (codigoCita.isEmpty() || diagnostico.isEmpty() || tratamiento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el código de la cita, diagnóstico y tratamiento.");
            return;
        }
        if (!codigoCita.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El código de cita debe ser un número.");
            return;
        }

        String sqlVerificar = "SELECT codigo_cita FROM citas_veterinarias WHERE codigo_cita = ?";
        String sqlInsertar = "INSERT INTO diagnosticos_tratamientos (codigo_cita, diagnostico, tratamiento) VALUES (?, ?, ?)";
        String sqlActualizar = "UPDATE diagnosticos_tratamientos SET diagnostico = ?, tratamiento = ? WHERE codigo_cita = ?";

        try (Connection conn = connect();
             PreparedStatement pstmtVerificar = conn.prepareStatement(sqlVerificar)) {

            pstmtVerificar.setInt(1, Integer.parseInt(codigoCita));
            ResultSet rs = pstmtVerificar.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "No existe una cita con este código.");
                return;
            }

            try (PreparedStatement pstmtInsertar = conn.prepareStatement(sqlInsertar);
                 PreparedStatement pstmtActualizar = conn.prepareStatement(sqlActualizar)) {

                // Verificar si ya existe un diagnóstico para la cita
                String sqlCheck = "SELECT codigo_cita FROM diagnosticos_tratamientos WHERE codigo_cita = ?";
                try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                    pstmtCheck.setInt(1, Integer.parseInt(codigoCita));
                    ResultSet rsCheck = pstmtCheck.executeQuery();

                    if (rsCheck.next()) {
                        // Si ya existe, se actualiza
                        pstmtActualizar.setString(1, diagnostico);
                        pstmtActualizar.setString(2, tratamiento);
                        pstmtActualizar.setInt(3, Integer.parseInt(codigoCita));
                        pstmtActualizar.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Diagnóstico y tratamiento actualizados con éxito.");
                    } else {
                        // Si no existe, se inserta
                        pstmtInsertar.setInt(1, Integer.parseInt(codigoCita));
                        pstmtInsertar.setString(2, diagnostico);
                        pstmtInsertar.setString(3, tratamiento);
                        pstmtInsertar.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Diagnóstico y tratamiento guardados con éxito.");
                    }
                }
            }

            // Limpiar los campos después de guardar
            limpiarCampos();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el diagnóstico y tratamiento.");
        }
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        textArea1.setText("");
        textArea2.setText("");
    }
}
