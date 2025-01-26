import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class fakemain {
    public static void main(String[] args) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Gatos", 40);
        dataset.setValue("Perros", 60);

        JFreeChart chart = ChartFactory.createPieChart("Mascotas", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame frame = new JFrame("Ejemplo de JFreeChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(chartPanel);
        frame.setVisible(true);
    }
}
