package PaqueteCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PaqueteRecursos.conexion;

public class agendarCitas extends conexion {
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

    public agendarCitas() {
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
                    JOptionPane.showMessageDialog(null, "Cita registrada correctamente.");

                    // Limpiar campos
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textArea1.setText("");
                    hembraCheckBox.setSelected(false);
                    machoCheckBox.setSelected(false);
                    chequeoGeneralCheckBox.setSelected(false);
                    cirugiaCheckBox.setSelected(false);
                    aseoCheckBox.setSelected(false);
                    vacunacionCheckBox.setSelected(false);
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
                new cliente();
            }
        });
    }
}
