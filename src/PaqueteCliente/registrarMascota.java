package PaqueteCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PaqueteRecursos.conexion;

public class registrarMascota extends conexion {
    public JPanel PAgendar;
    public JTextField textField1; // Cedula
    public JTextField textField2; // Tipo Mascota
    public JTextField textField3; // Nombre Mascota
    public JButton subirFotoButton;
    public JCheckBox chequeoGeneralCheckBox;
    public JCheckBox vacunacionCheckBox;
    public JCheckBox cirugiaCheckBox;
    public JCheckBox aseoCheckBox;
    public JCheckBox hembraCheckBox;
    public JCheckBox machoCheckBox;
    public JTextField textField4; // Motivo Cita
    public JTextArea textArea1; // Observaciones
    public JButton agendarCitaButton;
    public JButton regresarButton;
    public JLabel fotoLabel;

    public File fotoMascota; // Archivo seleccionado

    public registrarMascota() {

        JFrame frame = new JFrame("Registrar Mascota");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/regmascota.png").getImage());
        frame.setMinimumSize(new Dimension(800, 600));

        // Crear un panel personalizado que muestra la imagen de fondo
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/PaqueteRecursos/fondos/cliente.jpeg");
        backgroundPanel.setLayout(new BorderLayout());

        // Hacer el panel PAgendar transparente para ver la imagen de fondo
        PAgendar.setOpaque(false);
        backgroundPanel.add(PAgendar, BorderLayout.CENTER);

        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);

        //Botones
        ButtonGroup grupoSexoMascota = new ButtonGroup();
        grupoSexoMascota.add(hembraCheckBox);
        grupoSexoMascota.add(machoCheckBox);

        ButtonGroup grupoTipoServicio = new ButtonGroup();
        grupoTipoServicio.add(aseoCheckBox);
        grupoTipoServicio.add(cirugiaCheckBox);
        grupoTipoServicio.add(chequeoGeneralCheckBox);
        grupoTipoServicio.add(vacunacionCheckBox);

        subirFotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    fotoMascota = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(fotoMascota.getAbsolutePath());
                    Image image = imageIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                    fotoLabel.setIcon(new ImageIcon(image));
                }
            }
        });

        agendarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaCliente = textField1.getText();
                String tipoMascota = textField2.getText();
                String nombreMascota = textField3.getText();
                String sexoMascota = hembraCheckBox.isSelected() ? "hembra" : machoCheckBox.isSelected() ? "macho" : "";
                String motivoCita = textField4.getText();
                String observaciones = textArea1.getText();

                // Obtener tipo de servicio seleccionado
                String tipoServicio = "";
                if (chequeoGeneralCheckBox.isSelected()) {
                    tipoServicio = "Chequeo General";
                } else if (cirugiaCheckBox.isSelected()) {
                    tipoServicio = "Cirugía";
                } else if (aseoCheckBox.isSelected()) {
                    tipoServicio = "Aseo";
                } else if (vacunacionCheckBox.isSelected()) {
                    tipoServicio = "Vacunación";
                }

                if (cedulaCliente.isEmpty() || tipoMascota.isEmpty() || nombreMascota.isEmpty() ||
                        sexoMascota.isEmpty() || tipoServicio.isEmpty() || motivoCita.isEmpty() || fotoMascota == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos y suba una foto.");
                    return;
                }

                try (Connection conn = connect()) {
                    String sql = "INSERT INTO agendar_citas (cedula, tipo_mascota, nombre_mascota, " +
                            "foto_mascota, sexo_mascota, tipo_servicio, motivo_cita, observaciones) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setString(1, cedulaCliente);
                    pstmt.setString(2, tipoMascota);
                    pstmt.setString(3, nombreMascota);

                    // Leer la foto como flujo binario
                    FileInputStream fis = new FileInputStream(fotoMascota);
                    pstmt.setBinaryStream(4, fis, (int) fotoMascota.length());

                    pstmt.setString(5, sexoMascota);
                    pstmt.setString(6, tipoServicio);
                    pstmt.setString(7, motivoCita);
                    pstmt.setString(8, observaciones);

                    pstmt.execute();

                    // ACTUALIZAR ESTADÍSTICAS
                    // 1. Actualizar el conteo de tipo de servicio
                    String sqlUpdateServicio = "INSERT INTO estadisticas (categoria, valor, cantidad) VALUES ('servicio', ?, 1) " +
                            "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";
                    PreparedStatement pstmtServicio = conn.prepareStatement(sqlUpdateServicio);
                    pstmtServicio.setString(1, tipoServicio);
                    pstmtServicio.executeUpdate();

                    // 2. Actualizar el conteo de sexo de la mascota
                    String sqlUpdateSexo = "INSERT INTO estadisticas (categoria, valor, cantidad) VALUES ('sexo', ?, 1) " +
                            "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";
                    PreparedStatement pstmtSexo = conn.prepareStatement(sqlUpdateSexo);
                    pstmtSexo.setString(1, sexoMascota);
                    pstmtSexo.executeUpdate();

                    // 3. Actualizar el conteo de tipo de mascota
                    String sqlUpdateMascota = "INSERT INTO estadisticas (categoria, valor, cantidad) VALUES ('tipo_mascota', ?, 1) " +
                            "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";
                    PreparedStatement pstmtMascota = conn.prepareStatement(sqlUpdateMascota);
                    pstmtMascota.setString(1, tipoMascota);
                    pstmtMascota.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cita registrada correctamente.");

                    // Limpiar campos
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textArea1.setText("");
                    grupoSexoMascota.clearSelection();
                    grupoTipoServicio.clearSelection();
                    fotoLabel.setIcon(null);
                    fotoMascota = null;

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar la cita.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al procesar la imagen.");
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new cliente();
            }
        });
    }

    public static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen de fondo.");
            }

            // Redibujar la imagen cuando la ventana cambie de tamaño
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (backgroundImage != null) {
                        backgroundImage = new ImageIcon(imagePath).getImage().getScaledInstance(
                                getWidth(), getHeight(), Image.SCALE_SMOOTH);
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
