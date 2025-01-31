package PaqueteAdministrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import PaqueteRecursos.conexion;

public class estadisticas extends conexion {
    public JPanel PEstadisticas;
    public JTabbedPane tabbedPane1;
    private JButton verGráficaEstadísticaButton;
    private JButton regresarButton;
    public JPanel panelT;
    public JPanel panelT1;
    public JPanel panelT2;
    private JButton verGráficaEstadísticaButton1;
    private JButton verGráficaEstadísticaButton2;


    public estadisticas() {

        JFrame menuFrame = new JFrame("Pantalla de Administrador");
        menuFrame.setContentPane(PEstadisticas);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 600);
        menuFrame.setPreferredSize(new Dimension(1000, 600));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/estadisticas.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuFrame.pack();
        menuFrame.setVisible(true);

        verGráficaEstadísticaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarGraficaServicios();
            }
        });

        verGráficaEstadísticaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarGraficaSexo();
            }
        });

        verGráficaEstadísticaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarGraficaTipoAnimal();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new administrador();
            }
        });
    }

    public void generarGraficaServicios() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection conn = connect()) {
            String sql = "SELECT valor, cantidad FROM estadisticas WHERE categoria = 'servicio'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getInt("cantidad"), "Servicios", rs.getString("valor"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las estadísticas de servicios.");
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Servicios Más Usados",
                "Servicio",
                "Cantidad",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        panelT.removeAll();
        panelT.setLayout(new BorderLayout());
        panelT.add(chartPanel, BorderLayout.CENTER);
        panelT.revalidate();
        panelT.repaint();
    }

    public void generarGraficaSexo() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (Connection conn = connect()) {
            String sql = "SELECT valor, cantidad FROM estadisticas WHERE categoria = 'sexo'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataset.setValue(rs.getString("valor"), rs.getInt("cantidad"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las estadísticas de sexo.");
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribución por Sexo",
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        panelT1.removeAll();
        panelT1.setLayout(new BorderLayout());
        panelT1.add(chartPanel, BorderLayout.CENTER);
        panelT1.revalidate();
        panelT1.repaint();
    }

    public void generarGraficaTipoAnimal() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection conn = connect()) {
            String sql = "SELECT valor, cantidad FROM estadisticas WHERE categoria = 'tipo_mascota'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getInt("cantidad"), "Tipos de Mascota", rs.getString("valor"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las estadísticas de tipo de mascota.");
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Distribución de Tipos de Mascotas",
                "Tipo de Mascota",
                "Cantidad",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        panelT2.removeAll();
        panelT2.setLayout(new BorderLayout());
        panelT2.add(chartPanel, BorderLayout.CENTER);
        panelT2.revalidate();
        panelT2.repaint();
    }

}
