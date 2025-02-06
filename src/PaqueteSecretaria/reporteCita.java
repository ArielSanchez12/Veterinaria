package PaqueteSecretaria;
import PaqueteRecursos.conexion;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reporteCita extends conexion{
    public JButton crearReporteDeDiagnosticoButton;
    public JButton regresarButton;
    public JButton imprimirReportePDFButton;
    public JTextField textField1;
    public JLabel LCodCita;
    public JLabel LCedula;
    public JLabel LTipoMascota;
    public JLabel LDiagnostico;
    public JLabel LFechaHora;
    public JLabel LNombreMascota;
    public JLabel LSexoMascota;
    public JLabel LTratamiento;

    public reporteCita() {

        JFrame frameReporteCita = new JFrame("Reportes de Citas Médicas");
        frameReporteCita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameReporteCita.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/graficas.png")).getImage());
        frameReporteCita.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        frameReporteCita.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PReportesCitas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                InputStream imgStream = getClass().getClassLoader().getResourceAsStream("PaqueteRecursos/fondos/secre.jpeg");
                if (imgStream != null) {
                    try {
                        BufferedImage background = ImageIO.read(imgStream);
                        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("No se pudo cargar la imagen.");
                }
            }
        };
        PReportesCitas.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, -15, 20, 22); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Buscar cita para generar un Reporte", SwingConstants.CENTER);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

        JLabel ingresarCodCita = new JLabel("Buscar por codigo de la cita", SwingConstants.CENTER);
        ingresarCodCita.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        ingresarCodCita.setForeground(new Color(255, 255, 255));
        ingresarCodCita.setOpaque(true);
        ingresarCodCita.setBackground(Color.BLACK);

        textField1 = new JTextField(20);
        textField1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));

        JLabel tituloVet = new JLabel("Pet Care Pro", SwingConstants.CENTER);
        tituloVet.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        tituloVet.setForeground(new Color(255, 255, 255));
        tituloVet.setOpaque(true);
        tituloVet.setBackground(Color.BLACK);

        JLabel L1 = new JLabel("Codigo de cita", SwingConstants.CENTER);
        L1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L1.setForeground(new Color(255, 255, 255));
        L1.setOpaque(true);
        L1.setBackground(Color.BLACK);

        JLabel L2 = new JLabel("Cedula del dueño", SwingConstants.CENTER);
        L2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L2.setForeground(new Color(255, 255, 255));
        L2.setOpaque(true);
        L2.setBackground(Color.BLACK);

        JLabel L3 = new JLabel("Tipo de mascota", SwingConstants.CENTER);
        L3.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L3.setForeground(new Color(255, 255, 255));
        L3.setOpaque(true);
        L3.setBackground(Color.BLACK);

        JLabel L4 = new JLabel("Diagnostico", SwingConstants.CENTER);
        L4.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L4.setForeground(new Color(255, 255, 255));
        L4.setOpaque(true);
        L4.setBackground(Color.BLACK);

        JLabel L5 = new JLabel("Fecha y hora", SwingConstants.CENTER);
        L5.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L5.setForeground(new Color(255, 255, 255));
        L5.setOpaque(true);
        L5.setBackground(Color.BLACK);

        JLabel L6 = new JLabel("Nombre de la mascota", SwingConstants.CENTER);
        L6.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L6.setForeground(new Color(255, 255, 255));
        L6.setOpaque(true);
        L6.setBackground(Color.BLACK);

        JLabel L7 = new JLabel("Sexo de la mascota", SwingConstants.CENTER);
        L7.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L7.setForeground(new Color(255, 255, 255));
        L7.setOpaque(true);
        L7.setBackground(Color.BLACK);

        JLabel L8 = new JLabel("Tratamiento", SwingConstants.CENTER);
        L8.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        L8.setForeground(new Color(255, 255, 255));
        L8.setOpaque(true);
        L8.setBackground(Color.BLACK);

        // Inicialización de los JLabel para los datos obtenidos de la base de datos con alineación centrada
        LCodCita = new JLabel("", SwingConstants.CENTER);
        LCodCita.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LCodCita.setForeground(new Color(0, 0, 0));
        LCodCita.setOpaque(true);
        LCodCita.setBackground(Color.GRAY);

        LCedula = new JLabel("", SwingConstants.CENTER);
        LCedula.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LCedula.setForeground(new Color(0, 0, 0));
        LCedula.setOpaque(true);
        LCedula.setBackground(Color.GRAY);

        LTipoMascota = new JLabel("", SwingConstants.CENTER);
        LTipoMascota.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LTipoMascota.setForeground(new Color(0, 0, 0));
        LTipoMascota.setOpaque(true);
        LTipoMascota.setBackground(Color.GRAY);

        LDiagnostico = new JLabel("", SwingConstants.CENTER);
        LDiagnostico.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LDiagnostico.setForeground(new Color(0, 0, 0));
        LDiagnostico.setOpaque(true);
        LDiagnostico.setBackground(Color.GRAY);

        LFechaHora = new JLabel("", SwingConstants.CENTER);
        LFechaHora.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LFechaHora.setForeground(new Color(0, 0, 0));
        LFechaHora.setOpaque(true);
        LFechaHora.setBackground(Color.GRAY);

        LNombreMascota = new JLabel("", SwingConstants.CENTER);
        LNombreMascota.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LNombreMascota.setForeground(new Color(0, 0, 0));
        LNombreMascota.setOpaque(true);
        LNombreMascota.setBackground(Color.GRAY);

        LSexoMascota = new JLabel("", SwingConstants.CENTER);
        LSexoMascota.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LSexoMascota.setForeground(new Color(0, 0, 0));
        LSexoMascota.setOpaque(true);
        LSexoMascota.setBackground(Color.GRAY);

        LTratamiento = new JLabel("", SwingConstants.CENTER);
        LTratamiento.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        LTratamiento.setForeground(new Color(0, 0, 0));
        LTratamiento.setOpaque(true);
        LTratamiento.setBackground(Color.GRAY);

        // Ajustar el GridBagConstraints para que los datos se alineen correctamente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Mantener el llenado horizontal
        gbc.anchor = GridBagConstraints.CENTER;   // Asegurarse de que los componentes estén centrados

        crearReporteDeDiagnosticoButton = new JButton("Crear Reporte de Diagnostico y Tratamiento");
        crearReporteDeDiagnosticoButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        crearReporteDeDiagnosticoButton.setPreferredSize(new Dimension(500, 100));

        imprimirReportePDFButton = new JButton("Guardar Reporte (PDF)");
        imprimirReportePDFButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        imprimirReportePDFButton.setPreferredSize(new Dimension(500, 100));

        regresarButton = new JButton("Regresar");
        regresarButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(500, 100));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 0; PReportesCitas.add(titulo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PReportesCitas.add(ingresarCodCita, gbc);
        gbc.gridx = 1; gbc.gridy = 1; PReportesCitas.add(textField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PReportesCitas.add(tituloVet, gbc);
        gbc.gridx = 0; gbc.gridy = 3; PReportesCitas.add(L1, gbc);
        gbc.gridy = 4; PReportesCitas.add(LCodCita, gbc);
        gbc.gridy = 5; PReportesCitas.add(L2, gbc);
        gbc.gridy = 6; PReportesCitas.add(LCedula, gbc);
        gbc.gridy = 7; PReportesCitas.add(L3, gbc);
        gbc.gridy = 8; PReportesCitas.add(LTipoMascota, gbc);
        gbc.gridy = 9; PReportesCitas.add(L4, gbc);
        gbc.gridy = 10; PReportesCitas.add(LDiagnostico, gbc);
        gbc.gridx = 2; gbc.gridy = 3; PReportesCitas.add(L5, gbc);
        gbc.gridy = 4; PReportesCitas.add(LFechaHora, gbc);
        gbc.gridy = 5; PReportesCitas.add(L6, gbc);
        gbc.gridy = 6; PReportesCitas.add(LNombreMascota, gbc);
        gbc.gridy = 7; PReportesCitas.add(L7, gbc);
        gbc.gridy = 8; PReportesCitas.add(LSexoMascota, gbc);
        gbc.gridy = 9; PReportesCitas.add(L8, gbc);
        gbc.gridy = 10; PReportesCitas.add(LTratamiento, gbc);
        gbc.gridx = 1; gbc.gridy = 11; PReportesCitas.add(crearReporteDeDiagnosticoButton, gbc);
        gbc.gridx = 1; gbc.gridy = 12; PReportesCitas.add(imprimirReportePDFButton, gbc);
        gbc.gridx = 1; gbc.gridy = 13; PReportesCitas.add(regresarButton, gbc);

        frameReporteCita.setContentPane(PReportesCitas);
        frameReporteCita.setVisible(true);

        crearReporteDeDiagnosticoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoCita = textField1.getText().trim();

                if (codigoCita.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo de código de cita no puede estar vacío.");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "SELECT c.codigo_cita, c.fecha, c.hora, c.cedula, c.nombre_mascota, c.tipo_mascota, c.sexo_mascota, " +
                            "d.diagnostico, d.tratamiento " +
                            "FROM citas_veterinarias c " +
                            "LEFT JOIN diagnosticos_tratamientos d ON c.codigo_cita = d.codigo_cita " +
                            "WHERE c.codigo_cita = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, codigoCita);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        LCodCita.setText(rs.getString("codigo_cita"));
                        LFechaHora.setText(rs.getString("fecha") + " " + rs.getString("hora"));
                        LCedula.setText(rs.getString("cedula"));
                        LNombreMascota.setText(rs.getString("nombre_mascota"));
                        LTipoMascota.setText(rs.getString("tipo_mascota"));
                        LSexoMascota.setText(rs.getString("sexo_mascota"));
                        LDiagnostico.setText(rs.getString("diagnostico") != null ? rs.getString("diagnostico") : "No registrado");
                        LTratamiento.setText(rs.getString("tratamiento") != null ? rs.getString("tratamiento") : "No registrado");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró ninguna cita con el código ingresado.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al recuperar los datos de la cita.");
                }
            }
        });

        imprimirReportePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirReporteDiagnosticoPDF();
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameReporteCita.dispose();
                new secretaria();
            }
        });
    }

    private void imprimirReporteDiagnosticoPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Diagnóstico PDF");
        fileChooser.setSelectedFile(new File("ReporteDiagnostico.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Título del reporte
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph title = new Paragraph("Reporte de Diagnóstico y Tratamiento", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph(" "));

                // Crear tabla
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                // Agregar encabezados
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                table.addCell(new PdfPCell(new Phrase("Campo", headerFont)));
                table.addCell(new PdfPCell(new Phrase("Valor", headerFont)));

                // Agregar datos
                agregarFilaATabla(table, "Código de Cita", LCodCita.getText());
                agregarFilaATabla(table, "Fecha y Hora", LFechaHora.getText());
                agregarFilaATabla(table, "Cédula", LCedula.getText());
                agregarFilaATabla(table, "Nombre de la Mascota", LNombreMascota.getText());
                agregarFilaATabla(table, "Tipo de Mascota", LTipoMascota.getText());
                agregarFilaATabla(table, "Sexo de la Mascota", LSexoMascota.getText());
                agregarFilaATabla(table, "Diagnóstico", LDiagnostico.getText());
                agregarFilaATabla(table, "Tratamiento", LTratamiento.getText());

                document.add(table);
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

    private void agregarFilaATabla(PdfPTable table, String campo, String valor) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        table.addCell(new PdfPCell(new Phrase(campo, cellFont)));
        table.addCell(new PdfPCell(new Phrase(valor, cellFont)));
    }
}
