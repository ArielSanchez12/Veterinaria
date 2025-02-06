package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class historial extends conexion {
    public JButton verHistorialButton;
    public JButton regresarButton;
    public JButton imprimirPDFButton;
    public JScrollPane HistorialVet;
    public JTable historialTable;
    public JPanel PHistorial;  // Declarar PHistorial como atributo de la clase

    public historial() {

        JFrame menuFrame = new JFrame("Historial para el Veterinario");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/historial.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuFrame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        PHistorial = new JPanel() {  // Ahora es un atributo de la clase
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/vet.jpeg");
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PHistorial.setLayout(new GridBagLayout());

        GridBagConstraints verh = new GridBagConstraints();
        verh.insets = new Insets(15, 9, 35, 5);
        verh.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Ver el Historial Medico de las Mascotas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

        HistorialVet = new JScrollPane();
        HistorialVet.setPreferredSize(new Dimension(750, 200));

        verHistorialButton = new JButton("Ver Historial Medico");
        verHistorialButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        verHistorialButton.setPreferredSize(new Dimension(200, 50));

        imprimirPDFButton = new JButton("Guardar Historial (PDF)");
        imprimirPDFButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        imprimirPDFButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Regresar");
        regresarButton.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        verh.gridx = 1; verh.gridy = 0; PHistorial.add(titulo, verh);
        verh.gridx = 1; verh.gridy = 1; PHistorial.add(HistorialVet, verh);
        verh.gridx = 1; verh.gridy = 2; PHistorial.add(verHistorialButton, verh);
        verh.gridx = 1; verh.gridy = 3; PHistorial.add(imprimirPDFButton, verh);
        verh.gridx = 1; verh.gridy = 5; PHistorial.add(regresarButton, verh);

        // Mostrar el panel en el frame y hacerlo visible
        menuFrame.setContentPane(PHistorial);
        menuFrame.setVisible(true);

        historialTable = new JTable();
        HistorialVet.setViewportView(historialTable);

        verHistorialButton.addActionListener(e -> cargarHistorial());

        regresarButton.addActionListener(e -> {
            menuFrame.dispose();
            new veterinario();
        });

        imprimirPDFButton.addActionListener(e -> imprimirPDF());
    }

    private void cargarHistorial() {
        try (Connection conn = connect()) {
            String sql = "SELECT codigo_cita, cedula, fecha, hora, tipo_mascota, nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, costo FROM citas_veterinarias";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            String[] columnas = {"Código Cita", "Cédula", "Fecha", "Hora", "Tipo Mascota", "Nombre Mascota", "Sexo", "Tipo Servicio", "Motivo", "Costo"};
            DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

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
            JOptionPane.showMessageDialog(PHistorial, "Error al cargar el historial: " + e.getMessage());
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

                document.add(new Paragraph("RECIBOS DE LAS CONSULTAS VETERINARIAS",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)));
                document.add(new Paragraph(" "));

                String sql = "SELECT codigo_cita, cedula, fecha, hora, tipo_mascota, nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, costo FROM citas_veterinarias";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
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
                    document.add(new Paragraph(" "));
                }

                document.close();
                JOptionPane.showMessageDialog(PHistorial, "PDF generado exitosamente: " + fileToSave.getAbsolutePath());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(PHistorial, "Error al generar el PDF: " + e.getMessage());
            }
        }
    }
}
