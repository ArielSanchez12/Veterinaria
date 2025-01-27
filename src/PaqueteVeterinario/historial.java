package PaqueteVeterinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class historial extends JFrame {
    private JTable table1;
    private JTextArea textdiagnostico;
    private JButton imprimirButton;
    private JButton regresarButton;

    public historial() {
        // Configuración del formulario
        setTitle("Historial Médico");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel titulo = new JLabel("Historial Médico", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Configurar tabla
        String[] columnas = {"Fecha", "Médico", "Descripción"};
        Object[][] datos = {{"2024-01-15", "Dr. Pérez", "Consulta general"}};
        table1 = new JTable(datos, columnas);
        table1.setPreferredSize(new Dimension(500, 100)); // Configurar tamaño de la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.add(table1); // Agregar tabla directamente al panel
        add(panelTabla, BorderLayout.CENTER);

        // Área de diagnóstico
        JPanel panelDiagnostico = new JPanel(new BorderLayout());
        JLabel labelDiagnostico = new JLabel("Diagnóstico:");
        textdiagnostico = new JTextArea(5, 30); // Área de texto fija
        panelDiagnostico.add(labelDiagnostico, BorderLayout.NORTH);
        panelDiagnostico.add(textdiagnostico, BorderLayout.CENTER); // Agregar área de texto directamente
        add(panelDiagnostico, BorderLayout.SOUTH);

        // Botones
        JPanel panelBotones = new JPanel();
        imprimirButton = new JButton("Imprimir PDF");
        regresarButton = new JButton("Regresar");
        panelBotones.add(imprimirButton);
        panelBotones.add(regresarButton);
        add(panelBotones, BorderLayout.PAGE_END);

        // Listeners de botones
        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al presionar "Imprimir PDF"
                JOptionPane.showMessageDialog(null, "Imprimiendo PDF...");
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al presionar "Regresar"
                dispose(); // Cierra esta ventana
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(historial::new);
    }
}
