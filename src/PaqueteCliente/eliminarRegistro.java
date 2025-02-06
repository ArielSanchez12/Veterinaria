package PaqueteCliente;

import PaqueteAdministrador.administrador;
import PaqueteRecursos.conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eliminarRegistro extends conexion {
    public JTextField textField1;
    public JButton eliminarRegistroButton;
    public JButton regresarButton;

    public eliminarRegistro() {

        JFrame menuFrame = new JFrame("Eliminar registro");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/elim.png").getImage());
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        menuFrame.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PEliminarR = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon("src/PaqueteRecursos/fondos/cliente.jpeg"); // Imagen de fondo
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        PEliminarR.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 22); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Eliminar el Registro de la Mascota", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

        JLabel LCedula = new JLabel("Buscar y eliminar por cedula");
        LCedula.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);

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

        menuFrame.setContentPane(PEliminarR);
        menuFrame.setVisible(true);

        // Configurar botón para eliminar registro
        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaRegistro = textField1.getText().trim();

                if (cedulaRegistro.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una cedula válido.");
                    return;
                }

                // Confirmar acción
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de que desea eliminar el registro con la cedula: " + cedulaRegistro + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarRegistro(cedulaRegistro);
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new cliente();
            }
        });
    }

    private void eliminarRegistro(String idRegistro) {
        try (Connection conn = connect()) {

            String sql = "DELETE FROM agendar_citas WHERE cedula = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, idRegistro);


            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado con exito.");
                textField1.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro un registro con la cedula proporcionado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro.");
        }
    }
}
