package PaqueteRecursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inicio {
    public JPanel PInicio;
    private JButton registroParaClientesNuevosButton;
    private JButton inicioDeSesiónButton;
    private JButton salirButton;
    private JLabel pie;

    public inicio() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear el marco principal
        JFrame menuFrame = new JFrame("Inicio");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 600);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/pet-care-pro.png").getImage());

        // Establecer el marco en pantalla completa
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel principal con fondo
        PInicio = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("src/PaqueteRecursos/fondos/inicio-fondo.jpeg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public void doLayout() {
                super.doLayout();
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Calcular posiciones dinámicamente
                int buttonWidth = 300;
                int buttonHeight = 40;
                int buttonX = (panelWidth - buttonWidth + 37) / 2;

                // Margen entre botones
                int buttonSpacing = 30;
                int firstButtonY = (int) (panelHeight * 0.6); // 60% desde el inicio de la pantalla

                // Ajustar los botones
                registroParaClientesNuevosButton.setBounds(buttonX, firstButtonY, buttonWidth, buttonHeight);
                inicioDeSesiónButton.setBounds(buttonX, firstButtonY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
                salirButton.setBounds(buttonX, firstButtonY + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);

                // Ajustar el pie de página
                int pieWidth = 290; // Ancho fijo del pie
                int pieHeight = 10; // Altura fija del pie
                int pieX = (panelWidth - pieWidth + 37) / 2; // Centrar horizontalmente
                int pieY = panelHeight - pieHeight - 10; // A 10 píxeles del borde inferior
                pie.setBounds(pieX, pieY, pieWidth, pieHeight);
            }
        };
        PInicio.setLayout(null); // Seguimos usando un layout nulo para los cálculos manuales


        /*
        JLabel titulo = new JLabel("Bienvenidos a PetCarePro", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(60, 90, 200));
        titulo.setBounds(500, 20, 400, 50); // Posicionamos manualmente
        PInicio.add(titulo);

         */

        // Botones
        registroParaClientesNuevosButton = new JButton("Registro para Clientes Nuevos");
        registroParaClientesNuevosButton.setFont(new Font("Arial", Font.PLAIN, 18));
        registroParaClientesNuevosButton.setBackground(new Color(173, 216, 230));
        //registroParaClientesNuevosButton.setBounds(550, 400, 300, 40); // Posicionamos manualmente
        PInicio.add(registroParaClientesNuevosButton);

        inicioDeSesiónButton = new JButton("Inicio de Sesión");
        inicioDeSesiónButton.setFont(new Font("Arial", Font.PLAIN, 18));
        inicioDeSesiónButton.setBackground(new Color(173, 216, 230));
        //inicioDeSesiónButton.setBounds(550, 475, 300, 40); // Posicionamos manualmente
        PInicio.add(inicioDeSesiónButton);

        salirButton = new JButton("Salir");
        salirButton.setFont(new Font("Arial", Font.PLAIN, 18));
        salirButton.setBackground(new Color(255, 182, 193));
        //salirButton.setBounds(550, 550, 300, 40); // Posicionamos manualmente
        PInicio.add(salirButton);

        // Pie de página

        pie.setText("© 2025 PetCarePro - Todos los derechos reservados");
        pie.setFont(new Font("Arial", Font.ITALIC, 12));
        pie.setForeground(Color.DARK_GRAY);
        //pie.setBounds(500, 680, 400, 30); // Posicionamos manualmente
        PInicio.add(pie);


        menuFrame.setContentPane(PInicio);
        menuFrame.setVisible(true);

        // Listeners
        registroParaClientesNuevosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registro();
                menuFrame.dispose();
            }
        });

        inicioDeSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login();
                menuFrame.dispose();
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
