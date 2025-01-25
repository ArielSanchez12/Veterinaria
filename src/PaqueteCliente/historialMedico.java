package PaqueteCliente;

import PaqueteRecursos.conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;

public class historialMedico extends conexion {
    public JPanel PHistorial;
    public JButton verHistorialMedicoButton;
    public JButton regresarButton;
    public JScrollPane HISTORIAL; // ScrollPane para contener la tabla

    public historialMedico() {

        // Configurar el botón para cargar el historial médico
        verHistorialMedicoButton.addActionListener(e -> cargarHistorialMedico());
        regresarButton.addActionListener(e -> new cliente());
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
                // Recuperar los datos de la base de datos
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
                    Image imgEscalada = imagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    foto = new ImageIcon(imgEscalada);
                }

                // Agregar una fila al modelo de la tabla (incluyendo la cédula)
                modeloTabla.addRow(new Object[]{cedulaCliente, tipoMascota, nombreMascota, sexoMascota, tipoServicio, motivoCita, foto});
            }

            // Crear la tabla con el modelo
            JTable tabla = new JTable(modeloTabla);

            // Personalizar el renderizado de la columna de fotos
            tabla.getColumn("Foto").setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    // Si la celda contiene una imagen, mostrarla en un JLabel
                    if (value instanceof ImageIcon) {
                        JLabel etiquetaImagen = new JLabel((ImageIcon) value);
                        etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
                        return etiquetaImagen;
                    }
                    // Si no hay imagen, mostrar "Sin Imagen"
                    JLabel etiquetaTexto = new JLabel("Sin Imagen");
                    etiquetaTexto.setHorizontalAlignment(SwingConstants.CENTER);
                    return etiquetaTexto;
                }
            });

            // Configurar la tabla para que sea más agradable
            tabla.setRowHeight(120); // Altura de las filas para acomodar imágenes grandes
            tabla.setFillsViewportHeight(true);

            // Agregar la tabla a un JScrollPane
            JScrollPane scrollTabla = new JScrollPane(tabla);

            // Limpiar el contenido anterior del panel HISTORIAL
            HISTORIAL.setViewportView(scrollTabla);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el historial médico.");
        }
    }
}
