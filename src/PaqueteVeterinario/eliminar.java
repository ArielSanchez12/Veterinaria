package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import PaqueteRecursos.login;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class eliminar extends conexion {
    public JPanel PEliminarVet;
    public JButton eliminarButton;
    public JButton regresarButton;
    public JTextField textField1;

    public eliminar() {

        JFrame frameEliminarRegistroVeterinario = new JFrame("Eliminar registro del Veterinario");
        frameEliminarRegistroVeterinario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEliminarRegistroVeterinario.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/elim.png")).getImage());
        frameEliminarRegistroVeterinario.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre en pantalla completa
        frameEliminarRegistroVeterinario.setMinimumSize(new Dimension(800, 600));

        // Panel principal con fondo
        JPanel PEliminarVet = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                InputStream imgStream = getClass().getClassLoader().getResourceAsStream("PaqueteRecursos/fondos/vet.jpeg");
                if (imgStream != null) {
                    try {
                        BufferedImage background = ImageIO.read(imgStream);
                        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("No se pudo cargar la imagen.");
                }
            }
        };
        PEliminarVet.setLayout(new GridBagLayout()); // Diseño responsivo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, -15, 35, 22); // Aumentar espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel titulo = new JLabel("Eliminar el Registro Para el veterinario", SwingConstants.CENTER);
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

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setFont(new Font("Arial", Font.BOLD, 18));
        eliminarButton.setPreferredSize(new Dimension(200, 50));

        regresarButton = new JButton("Regresar al Inicio");
        regresarButton.setFont(new Font("Arial", Font.BOLD, 18));
        regresarButton.setPreferredSize(new Dimension(200, 50));

        // Agregar componentes al panel
        gbc.gridx = 1; gbc.gridy = 0; PEliminarVet.add(titulo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; PEliminarVet.add(LCedula, gbc);
        gbc.gridx = 1; gbc.gridy = 1; PEliminarVet.add(textField1, gbc);
        gbc.gridx = 1; gbc.gridy = 2; PEliminarVet.add(eliminarButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; PEliminarVet.add(regresarButton, gbc);

        frameEliminarRegistroVeterinario.setContentPane(PEliminarVet);
        frameEliminarRegistroVeterinario.setVisible(true);

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = textField1.getText().trim();  // Obtener el texto del campo de texto

                if (searchValue.isEmpty()) {
                    JOptionPane.showMessageDialog(PEliminarVet, "Por favor, ingrese un valor para buscar.");
                    return;
                }

                // Eliminar por código de cita o cédula
                try (Connection conn = connect()) {
                    // Usar OR en la consulta para buscar por código_cita o cédula
                    String sql = "DELETE FROM citas_veterinarias WHERE cedula = ? OR codigo_cita = ?";

                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, searchValue);  // Establecer el valor en la primera columna (cedula)
                    pstmt.setString(2, searchValue);  // Establecer el valor en la segunda columna (codigo_cita)

                    // Mostrar la consulta antes de ejecutarla para depuración
                    System.out.println("Ejecutando consulta: " + pstmt.toString());

                    int rowsAffected = pstmt.executeUpdate(); // Ejecutar la consulta

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(PEliminarVet, "Registro eliminado exitosamente.");
                        textField1.setText("");
                    } else {
                        JOptionPane.showMessageDialog(PEliminarVet, "No se encontró ningún registro con el valor ingresado.");
                    }

                } catch (Exception ex) {
                    // Mostrar detalles del error
                    JOptionPane.showMessageDialog(PEliminarVet, "Error al eliminar el registro: " + ex.getMessage());
                    ex.printStackTrace(); // Imprimir el error en la consola para depuración
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameEliminarRegistroVeterinario.dispose();
                new veterinario();
            }
        });
    }
}
