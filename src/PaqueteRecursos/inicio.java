package PaqueteRecursos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que representa la ventana de inicio de la aplicación.
 * Contiene opciones para registrar nuevos clientes, iniciar sesión o salir.
 */
public class inicio {
    /** Panel principal de la ventana de inicio. */
    public JPanel PInicio;

    /** Botón para registrar nuevos clientes. */
    private JButton registroParaClientesNuevosButton;

    /** Botón para iniciar sesión. */
    private JButton inicioDeSesiónButton;

    /** Botón para salir de la aplicación. */
    private JButton salirButton;

    /** Etiqueta para mostrar el pie de página. */
    private JLabel pie;

    /**
     * Constructor de la clase inicio.
     * Configura la interfaz gráfica, define la apariencia y asigna eventos a los botones.
     */
    public inicio() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creación y configuración de la ventana
        JFrame frameInicio = new JFrame("Inicio");
        frameInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInicio.setSize(1000, 600);
        frameInicio.setLocationRelativeTo(null);
        frameInicio.setIconImage(new ImageIcon(getClass().getResource("/PaqueteRecursos/iconos/inicio.png")).getImage());
        frameInicio.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Configuración del panel de inicio con imagen de fondo
        PInicio = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                InputStream imgStream = getClass().getClassLoader().getResourceAsStream("PaqueteRecursos/fondos/inicio.jpeg");
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

            @Override
            public void doLayout() {
                super.doLayout();
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                int buttonWidth = 300;
                int buttonHeight = 40;
                int buttonX = (panelWidth - buttonWidth + 37) / 2;
                int buttonSpacing = 30;
                int firstButtonY = (int) (panelHeight * 0.6);

                // Posicionamiento de los botones
                registroParaClientesNuevosButton.setBounds(buttonX, firstButtonY, buttonWidth, buttonHeight);
                inicioDeSesiónButton.setBounds(buttonX, firstButtonY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
                salirButton.setBounds(buttonX, firstButtonY + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);

                // Posicionamiento del pie de página
                int pieWidth = 290;
                int pieHeight = 10;
                int pieX = (panelWidth - pieWidth + 37) / 2;
                int pieY = panelHeight - pieHeight - 10;
                pie.setBounds(pieX, pieY, pieWidth, pieHeight);
            }
        };
        PInicio.setLayout(null);

        // Creación y configuración de botones
        registroParaClientesNuevosButton = new JButton("Registro para Clientes Nuevos");
        registroParaClientesNuevosButton.setFont(new Font("Arial", Font.PLAIN, 18));
        registroParaClientesNuevosButton.setBackground(new Color(173, 216, 230));
        PInicio.add(registroParaClientesNuevosButton);

        inicioDeSesiónButton = new JButton("Inicio de Sesión");
        inicioDeSesiónButton.setFont(new Font("Arial", Font.PLAIN, 18));
        inicioDeSesiónButton.setBackground(new Color(173, 216, 230));
        PInicio.add(inicioDeSesiónButton);

        salirButton = new JButton("Salir");
        salirButton.setFont(new Font("Arial", Font.PLAIN, 18));
        salirButton.setBackground(new Color(255, 182, 193));
        PInicio.add(salirButton);

        // Configuración del pie de página
        pie.setText("© 2025 PetCarePro - Todos los derechos reservados");
        pie.setFont(new Font("Arial", Font.ITALIC, 12));
        pie.setForeground(Color.DARK_GRAY);
        PInicio.add(pie);

        // Agregar el panel a la ventana y mostrarla
        frameInicio.setContentPane(PInicio);
        frameInicio.setVisible(true);

        // Eventos de los botones
        registroParaClientesNuevosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registro();
                frameInicio.dispose();
            }
        });

        inicioDeSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login();
                frameInicio.dispose();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}