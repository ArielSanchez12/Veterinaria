
package PaqueteCliente;

import PaqueteRecursos.conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class historialMedico extends conexion {
    public JPanel PHistorial;
    public JButton verHistorialMedicoButton;
    public JButton regresarButton;
    public JTable Table;

    public DefaultTableModel tableModel;

    public historialMedico() {
        // Configurar el modelo de la tabla
        String[] columnas = {"Tipo de Mascota", "Nombre de Mascota", "Foto", "Sexo", "Tipo de Servicio"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que las celdas no sean editables
            }
        };
        Table.setModel(tableModel);

        // Asignar el renderizador personalizado para la columna de fotos
        Table.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());

        // Cargar el historial médico
        verHistorialMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarHistorialMedico();
            }
        });

        // Regresar
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new cliente();
            }
        });

        // Añadir opción para modificar foto al hacer doble clic
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Doble clic
                    int row = Table.getSelectedRow();
                    if (row != -1) {
                        String nombreMascota = tableModel.getValueAt(row, 1).toString();
                        modificarFoto(nombreMascota);
                    }
                }
            }
        });
    }

    private void cargarHistorialMedico() {
        // Limpia los datos anteriores de la tabla
        tableModel.setRowCount(0);

        try (Connection conn = connect()) {
            String sql = "SELECT tipo_mascota, nombre_mascota, foto_mascota, sexo_mascota, tipo_servicio FROM agendar_citas";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String tipoMascota = rs.getString("tipo_mascota");
                String nombreMascota = rs.getString("nombre_mascota");
                String sexoMascota = rs.getString("sexo_mascota");
                String tipoServicio = rs.getString("tipo_servicio");
                byte[] fotoBytes = rs.getBytes("foto_mascota");

                // Convertir los bytes de la foto en un ImageIcon
                ImageIcon imagen = null;
                if (fotoBytes != null) {
                    Image img = Toolkit.getDefaultToolkit().createImage(fotoBytes);
                    imagen = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // Ajustar tamaño
                }

                // Agregar fila a la tabla
                tableModel.addRow(new Object[]{tipoMascota, nombreMascota, imagen, sexoMascota, tipoServicio});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el historial médico.");
        }
    }

    private void modificarFoto(String nombreMascota) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File nuevaFoto = fileChooser.getSelectedFile();

            try (Connection conn = connect()) {
                String sql = "UPDATE agendar_citas SET foto_mascota = ? WHERE nombre_mascota = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                FileInputStream fis = new FileInputStream(nuevaFoto);
                pstmt.setBinaryStream(1, fis, (int) nuevaFoto.length());
                pstmt.setString(2, nombreMascota);

                int filasActualizadas = pstmt.executeUpdate();
                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null, "Foto actualizada correctamente.");
                    cargarHistorialMedico(); // Refrescar la tabla
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar la foto.");
            }
        }
    }

    // Clase para renderizar imágenes en la tabla
    public static class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setText(value != null ? value.toString() : "");
                setIcon(null);
            }
            return this;
        }
    }
}
