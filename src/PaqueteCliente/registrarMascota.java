package PaqueteCliente;

import PaqueteRecursos.conexion;
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
import java.net.URL;


/**
 * La clase {@code registrarMascota} representa la interfaz gráfica para registrar la información de una mascota
 * y agendar una cita. Permite ingresar datos como cédula del cliente, tipo de mascota, nombre, sexo,
 * tipo de servicio requerido, motivo de la cita y observaciones. También permite subir una foto de la mascota.
 * Esta clase hereda de la clase `conexion` que probablemente gestiona la conexión a la base de datos.
 */
public class registrarMascota extends conexion {
    public JPanel PAgendar;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JButton subirFotoButton;
    public JCheckBox chequeoGeneralCheckBox;
    public JCheckBox vacunacionCheckBox;
    public JCheckBox cirugiaCheckBox;
    public JCheckBox aseoCheckBox;
    public JCheckBox hembraCheckBox;
    public JCheckBox machoCheckBox;
    public JTextField textField4;
    public JTextArea textArea1;
    public JButton agendarCitaButton;
    public JButton regresarButton;
    public JLabel fotoLabel;
    public JLabel LCed;
    public JLabel LTipoMas;
    public JLabel LNomMas;
    public JLabel LFotoMas;
    public JLabel LSexMas;
    public JLabel LTipoServ;
    public JLabel LMotivo;
    public JLabel LObserv;
    private JLabel LTitulo;
    private JLabel LFormato;
    public File fotoMascota;

    /**
     * Constructor de la clase `registrarMascota`.
     * Inicializa la interfaz gráfica, configura los componentes, establece los listeners para los botones
     * y maneja la lógica de registro de la cita.
     */
    public registrarMascota() {

        JFrame frameRegistrarMascota = new JFrame("Registrar Mascota");
        frameRegistrarMascota.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRegistrarMascota.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameRegistrarMascota.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/regmascota.png")).getImage());
        frameRegistrarMascota.setMinimumSize(new Dimension(800, 600));

        // Crear un panel personalizado que muestra la imagen de fondo
        BackgroundPanel backgroundPanel = new BackgroundPanel("/PaqueteRecursos/fondos/cliente.jpeg");
        backgroundPanel.setLayout(new BorderLayout());

        // Hacer el panel PAgendar transparente para ver la imagen de fondo
        PAgendar.setOpaque(false);
        backgroundPanel.add(PAgendar, BorderLayout.CENTER);

        LTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LTitulo.setForeground(new Color(255, 255, 255));
        LTitulo.setOpaque(true);
        LTitulo.setBackground(Color.BLACK);

        LCed.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        LCed.setFont(new Font("Arial", Font.BOLD, 15));
        LCed.setForeground(new Color(0, 0, 0));

        LTipoMas.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        LTipoMas.setFont(new Font("Arial", Font.BOLD, 15));
        LTipoMas.setForeground(new Color(0, 0, 0));

        LNomMas.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LNomMas.setFont(new Font("Arial", Font.BOLD, 15));
        LNomMas.setForeground(new Color(0, 0, 0));

        LFotoMas.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LFotoMas.setFont(new Font("Arial", Font.BOLD, 15));
        LFotoMas.setForeground(new Color(0, 0, 0));

        LFormato.setFont(new Font("Arial", Font.BOLD, 10));
        LFormato.setForeground(new Color(255, 0, 0));

        LSexMas.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LSexMas.setFont(new Font("Arial", Font.BOLD, 15));
        LSexMas.setForeground(new Color(0, 0, 0));

        LTipoServ.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LTipoServ.setFont(new Font("Arial", Font.BOLD, 15));
        LTipoServ.setForeground(new Color(0, 0, 0));

        LMotivo.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LMotivo.setFont(new Font("Arial", Font.BOLD, 15));
        LMotivo.setForeground(new Color(0, 0, 0));

        LObserv.setBorder(BorderFactory.createEmptyBorder(0, 180, 0, 0));
        LObserv.setFont(new Font("Arial", Font.BOLD, 15));
        LObserv.setForeground(new Color(0, 0, 0));


        frameRegistrarMascota.setContentPane(backgroundPanel);
        frameRegistrarMascota.setVisible(true);

        //Grupo de botones para que se seleccione uno a la vez
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
                frameRegistrarMascota.dispose();
                new cliente();
            }
        });
    }

    public static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                // Cargar imagen desde el classpath
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                } else {
                    System.out.println("No se pudo encontrar la imagen: " + imagePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Redibujar la imagen cuando la ventana cambie de tamaño
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (backgroundImage != null) {
                        backgroundImage = backgroundImage.getScaledInstance(
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