package PaqueteCliente;

import PaqueteRecursos.conexion;
import PaqueteRecursos.login;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;

public class historialMedico extends conexion {
    public JPanel PHistorial;
    public JButton verHistorialMedicoButton;
    public JButton regresarButton;
    public JScrollPane HISTORIAL; // ScrollPane para contener la tabla
    public JButton imprimirPDFButton;

    public historialMedico() {

        JFrame frame = new JFrame("Historial Medico de la Mascota");
        frame.setContentPane(PHistorial);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/historial-medico.png").getImage());
        frame.pack();
        frame.setVisible(true);

        // Configurar el botón para cargar el historial médico
        verHistorialMedicoButton.addActionListener(e -> cargarHistorialMedico());

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new cliente();
            }
        });

        // Acción para el botón de imprimir PDF
        imprimirPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirPDF();
            }
        });
    }

    private void cargarHistorialMedico() {
        try (Connection conn = connect()) {
            String sql = "SELECT cedula, tipo_mascota, nombre_mascota, foto_mascota, sexo_mascota, tipo_servicio, motivo_cita FROM agendar_citas";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Crear el modelo de tabla con la columna de cédula incluida
            String[] columnas = {"Cedula", "Tipo Mascota", "Nombre Mascota", "Sexo", "Tipo Servicio", "Motivo", "Foto"};
            DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que las celdas no sean editables
                }
            };

            // Cargar los datos en el modelo de la tabla
            while (rs.next()) {
                String cedulaCliente = rs.getString("cedula");
                String tipoMascota = rs.getString("tipo_mascota");
                String nombreMascota = rs.getString("nombre_mascota");
                String sexoMascota = rs.getString("sexo_mascota");
                String tipoServicio = rs.getString("tipo_servicio");
                String motivoCita = rs.getString("motivo_cita");
                byte[] fotoBytes = rs.getBytes("foto_mascota");

                // Crear un ícono de imagen para la foto
                ImageIcon foto = null;
                if (fotoBytes != null) {
                    ImageIcon imagen = new ImageIcon(fotoBytes);
                    java.awt.Image imgEscalada = imagen.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    foto = new ImageIcon(imgEscalada);
                }

                modeloTabla.addRow(new Object[]{cedulaCliente, tipoMascota, nombreMascota, sexoMascota, tipoServicio, motivoCita, foto});
            }

            JTable tabla = new JTable(modeloTabla);

            // Personalizar el renderizado de la columna de fotos
            tabla.getColumn("Foto").setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    if (value instanceof ImageIcon) {
                        JLabel etiquetaImagen = new JLabel((ImageIcon) value);
                        etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
                        return etiquetaImagen;
                    }
                    JLabel etiquetaTexto = new JLabel("Sin Imagen");
                    etiquetaTexto.setHorizontalAlignment(SwingConstants.CENTER);
                    return etiquetaTexto;
                }
            });

            tabla.setRowHeight(120);
            tabla.setFillsViewportHeight(true);

            HISTORIAL.setViewportView(tabla);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el historial médico.");
        }
    }

    private void imprimirPDF() {
        // Crear un JFileChooser para seleccionar la ubicación donde guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Establecer un nombre de archivo predeterminado
        fileChooser.setSelectedFile(new java.io.File("HistorialMedico.pdf"));

        // Mostrar el cuadro de diálogo
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try (Connection conn = connect()) {
                // Crear un documento PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Agregar título al PDF
                document.add(new Paragraph("Historial Médico de Mascotas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));
                document.add(new Paragraph(" ")); // Espacio

                // Leer los datos desde la base de datos
                String sql = "SELECT cedula, tipo_mascota, nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, foto_mascota FROM agendar_citas";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                // Crear tabla en el PDF con las mismas columnas que en la base de datos
                PdfPTable pdfTable = new PdfPTable(7); // Número de columnas
                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(10f);

                // Agregar encabezados de columna
                pdfTable.addCell(new PdfPCell(new Phrase("Cédula", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Tipo Mascota", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Nombre Mascota", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Sexo", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Tipo Servicio", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Motivo Cita", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));
                pdfTable.addCell(new PdfPCell(new Phrase("Foto", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))));

                // Agregar filas con los datos obtenidos
                while (rs.next()) {
                    pdfTable.addCell(rs.getString("cedula"));
                    pdfTable.addCell(rs.getString("tipo_mascota"));
                    pdfTable.addCell(rs.getString("nombre_mascota"));
                    pdfTable.addCell(rs.getString("sexo_mascota"));
                    pdfTable.addCell(rs.getString("tipo_servicio"));
                    pdfTable.addCell(rs.getString("motivo_cita"));

                    // Manejar la imagen (si existe)
                    byte[] fotoBytes = rs.getBytes("foto_mascota");
                    if (fotoBytes != null && fotoBytes.length > 0) {
                        try {
                            // Convertir los bytes en una imagen compatible con iText
                            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(fotoBytes);
                            img.scaleToFit(50, 50); // Ajustar tamaño
                            PdfPCell imgCell = new PdfPCell(img);
                            imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfTable.addCell(imgCell);
                        } catch (Exception e) {
                            // Manejar imágenes no válidas
                            pdfTable.addCell("Imagen no válida");
                        }
                    } else {
                        pdfTable.addCell("Sin Imagen");
                    }
                }

                // Agregar la tabla al documento
                document.add(pdfTable);

                // Cerrar el documento
                document.close();
                JOptionPane.showMessageDialog(null, "PDF generado exitosamente en: " + fileToSave.getAbsolutePath());

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al generar el PDF.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
        }
    }
}
