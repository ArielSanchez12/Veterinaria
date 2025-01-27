package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel; // Add

public class historial extends conexion {
    public JButton verHistorialButton;
    public JButton regresarButton;
    public JButton imprimirPDFButton;
    public JPanel PHistorial;
    public JScrollPane HistorialVet;
    public JTable historialTable;

    public historial() {
        historialTable = new JTable();
        HistorialVet.setViewportView(historialTable); // Agregar la tabla al JScrollPane

        verHistorialButton.addActionListener(e -> cargarHistorial());

        regresarButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(PHistorial);
            if (parentFrame != null) {
                parentFrame.dispose(); // Cierra la ventana actual
            }
        });

        imprimirPDFButton.addActionListener(e -> imprimirPDF());
    }

    private void cargarHistorial() {
        try (Connection conn = connect()) {
            String sql = "SELECT codigo_cita, cedula, fecha, hora, tipo_mascota, nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, costo FROM citas_veterinarias";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Crear el modelo de tabla
            String[] columnas = {"Código Cita", "Cédula", "Fecha", "Hora", "Tipo Mascota", "Nombre Mascota", "Sexo", "Tipo Servicio", "Motivo", "Costo"};
            DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que las celdas no sean editables
                }
            };

            // Cargar los datos en el modelo de la tabla
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("codigo_cita"),
                        rs.getString("cedula"),
                        rs.getDate("fecha"),
                        rs.getTime("hora"),
                        rs.getString("tipo_mascota"),
                        rs.getString("nombre_mascota"),
                        rs.getString("sexo_mascota"),
                        rs.getString("tipo_servicio"),
                        rs.getString("motivo_cita"),
                        rs.getDouble("costo")
                });
            }

            historialTable.setModel(modeloTabla);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el historial: " + e.getMessage());
        }
    }

    private void imprimirPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setSelectedFile(new java.io.File("HistorialVeterinario.pdf"));

        int userSelection = fileChooser.showSaveDialog(PHistorial);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try (Connection conn = connect()) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Agregar encabezado
                document.add(new Paragraph("RECIBOS DE LAS CONSULTAS VETERINARIAS",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)));
                document.add(new Paragraph(" ")); // Espacio

                // Consultar las citas
                String sql = "SELECT codigo_cita, cedula, fecha, hora, tipo_mascota, nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, costo FROM citas_veterinarias";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Crear el recibo para cada cita
                    document.add(new Paragraph("CODIGO CITA: " + rs.getInt("codigo_cita")));
                    document.add(new Paragraph("CEDULA: " + rs.getString("cedula")));
                    document.add(new Paragraph("FECHA: " + rs.getDate("fecha")));
                    document.add(new Paragraph("HORA: " + rs.getTime("hora")));
                    document.add(new Paragraph("TIPO MASCOTA: " + rs.getString("tipo_mascota")));
                    document.add(new Paragraph("NOMBRE MASCOTA: " + rs.getString("nombre_mascota")));
                    document.add(new Paragraph("SEXO: " + rs.getString("sexo_mascota")));
                    document.add(new Paragraph("TIPO SERVICIO: " + rs.getString("tipo_servicio")));
                    document.add(new Paragraph("MOTIVO DE LA CITA: " + rs.getString("motivo_cita")));
                    document.add(new Paragraph("COSTO: $" + rs.getDouble("costo")));
                    document.add(new Paragraph("----------------------------------------------------------------"));
                    document.add(new Paragraph(" ")); // Espacio entre citas
                }

                document.close();

                JOptionPane.showMessageDialog(PHistorial, "PDF generado exitosamente: " + fileToSave.getAbsolutePath());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(PHistorial, "Error al generar el PDF!");
            }
        }
    }
}
