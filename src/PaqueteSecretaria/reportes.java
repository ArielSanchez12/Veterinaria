package PaqueteSecretaria;
import PaqueteRecursos.conexion;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reportes extends conexion {
    private JTabbedPane tabbedPane1;
    public JPanel PReportes;
    private JComboBox<Integer> comboBox1;
    private JComboBox<Integer> comboBox2;
    private JLabel LGanancias;
    private JLabel LPromedio;
    private JPanel PGraficaMonetaria;
    private JButton crearReporteButton;
    private JButton regresarButton;
    private JPanel PReporteDyT;
    private JTextField textField1;
    private JLabel LConclusion;
    private JButton imprimirReportePDFButton;
    private JButton imprimirReportePDFButton1;
    private JButton crearReporteDeDiagnosticoButton;
    private List<Double> costos;

    public reportes() {
        JFrame menuFrame = new JFrame("Pantalla de Reportes");
        menuFrame.setContentPane(PReportes);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(500, 300);
        menuFrame.setPreferredSize(new Dimension(500, 300));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.pack();
        menuFrame.setVisible(true);

        // Llenar los JComboBox
        for (int month = 1; month <= 12; month++) {
            comboBox1.addItem(month);
        }
        for (int year = 2015; year <= 2025; year++) {
            comboBox2.addItem(year);
        }

        crearReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });

        imprimirReportePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirReportePDF();
            }
        });
        crearReporteDeDiagnosticoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        imprimirReportePDFButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void generarReporte() {
        int month = (int) comboBox1.getSelectedItem();
        int year = (int) comboBox2.getSelectedItem();

        costos = obtenerCostosDesdeBD(year, month);
        if (costos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos para la fecha seleccionada.");
            return;
        }

        double total = costos.stream().mapToDouble(Double::doubleValue).sum();
        double promedio = total / costos.size();

        LGanancias.setText(String.format("$%.2f", total));
        LPromedio.setText(String.format("$%.2f", promedio));
        LConclusion.setText(String.format(
                "Se concluye que en el año %d mes %d las ganancias totales fueron $%.2f dólares, con un promedio de $%.2f dólares, por lo que no se percibe ninguna pérdida monetaria.",
                year, month, total, promedio
        ));

        mostrarGrafica(costos);
    }

    private List<Double> obtenerCostosDesdeBD(int year, int month) {
        List<Double> costos = new ArrayList<>();

        try (Connection conn = connect()) {
            String sql = "SELECT costo FROM citas_veterinarias WHERE YEAR(fecha) = ? AND MONTH(fecha) = ? ORDER BY fecha";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                costos.add(rs.getDouble("costo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return costos;
    }

    private void mostrarGrafica(List<Double> costos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < costos.size(); i++) {
            dataset.addValue(costos.get(i), "Costo", "Día " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Variación de Costos",
                "Día",
                "Costo ($)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 200));

        if (PGraficaMonetaria != null) {
            PGraficaMonetaria.removeAll();
            PGraficaMonetaria.setLayout(new BorderLayout());
            PGraficaMonetaria.add(chartPanel, BorderLayout.CENTER);
            PGraficaMonetaria.revalidate();
            PGraficaMonetaria.repaint();
        } else {
            System.err.println("Error: PGraficaMonetaria no está inicializado correctamente.");
        }
    }

    private void imprimirReportePDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte PDF");
        fileChooser.setSelectedFile(new File("ReporteFinanciero.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Título del reporte
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph title = new Paragraph("Reporte Financiero", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph(" "));

                // Agregar imagen de la gráfica
                JFreeChart chart = generarGraficaParaPDF();
                File chartImage = new File("temp_chart.png");
                ChartUtilities.saveChartAsPNG(chartImage, chart, 400, 200);
                Image image = Image.getInstance("temp_chart.png");
                image.scaleToFit(500, 300);
                document.add(image);
                document.add(new Paragraph(" "));

                // Agregar datos financieros
                Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
                document.add(new Paragraph("Ganancias Totales: " + LGanancias.getText(), dataFont));
                document.add(new Paragraph("Promedio de Ganancias: " + LPromedio.getText(), dataFont));
                document.add(new Paragraph(" "));

                // Agregar conclusión
                document.add(new Paragraph("Conclusión:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                document.add(new Paragraph(LConclusion.getText(), dataFont));

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

    private JFreeChart generarGraficaParaPDF() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < costos.size(); i++) {
            dataset.addValue(costos.get(i), "Costo", "Día " + (i + 1));
        }

        return ChartFactory.createLineChart(
                "Variación de Costos",
                "Día",
                "Costo ($)",
                dataset
        );
    }
}
