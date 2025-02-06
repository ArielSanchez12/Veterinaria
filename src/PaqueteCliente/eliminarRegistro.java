package PaqueteCliente;

import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para eliminar el registro de una mascota por número de cédula.
 * Extiende la clase conexion para manejar la conexión a la base de datos.
 */
public class eliminarRegistro extends conexion {
    private JTextField textField1;
    private JButton eliminarRegistroButton;
    private JButton regresarButton;

    /**
     * Constructor que inicializa la interfaz gráfica para eliminar registros.
     */
    public eliminarRegistro() {
        JFrame frameEliminarRegistroCliente = new JFrame("Eliminar registro");
        frameEliminarRegistroCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEliminarRegistroCliente.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/elim.png").getImage());
        frameEliminarRegistroCliente.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameEliminarRegistroCliente.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PEliminarR = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/cliente.jpeg");
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PEliminarR.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 22);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Eliminar el Registro de la Mascota", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

        JLabel LCedula = new JLabel("Buscar y eliminar por cédula");
        LCedula.setFont(new Font("Arial", Font.BOLD, 20));
        LCedula.setForeground(Color.WHITE);
        LCedula.setOpaque(true);
        LCedula.setBackground(Color.BLACK);

        textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));

        eliminarRegistroButton = new JButton("Eliminar el registro");
        eliminarRegistroButton.setFont(new Font("Arial", Font.BOLD, 18));
        eliminarRegistroButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Regresar al Inicio");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 0; PEliminarR.add(titulo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PEliminarR.add(LCedula, gbc);
        gbc.gridx = 1; gbc.gridy = 1; PEliminarR.add(textField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PEliminarR.add(eliminarRegistroButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PEliminarR.add(regresarButton, gbc);

        frameEliminarRegistroCliente.setContentPane(PEliminarR);
        frameEliminarRegistroCliente.setVisible(true);

        // Acción del botón para eliminar registro
        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaRegistro = textField1.getText().trim();

                if (cedulaRegistro.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una cédula válida.");
                    return;
                }

                // Confirmación de eliminación
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el registro con la cédula: " + cedulaRegistro + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarRegistro(cedulaRegistro);
                }
            }
        });

        // Acción del botón para regresar al menú del cliente
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameEliminarRegistroCliente.dispose();
                new cliente();
            }
        });
    }

    /**
     * Método que elimina un registro de la base de datos según la cédula proporcionada.
     * @param cedulaRegistro Número de cédula del registro a eliminar.
     */
    private void eliminarRegistro(String cedulaRegistro) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM agendar_citas WHERE cedula = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cedulaRegistro);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado con éxito.");
                textField1.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un registro con la cédula proporcionada.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro.");
        }
    }
}