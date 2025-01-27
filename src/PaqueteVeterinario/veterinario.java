package PaqueteVeterinario;

import PaqueteRecursos.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class veterinario {
    public JPanel PVeterinario;
    public JButton gestiónDeCitasButton;
    public JButton historialMédicoDeMascotasButton;
    public JButton eliminarCitaYTrataButton;
    public JButton salirButton;


    public veterinario() {
        JFrame menuFrame = new JFrame("Pantalla de Usuario");
        menuFrame.setContentPane(PVeterinario);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(600, 600);
        menuFrame.setPreferredSize(new Dimension(500, 500));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/veterinario.png").getImage());
        menuFrame.pack();
        menuFrame.setVisible(true);

        gestiónDeCitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                citas ventanaCitas = new citas();
                ventanaCitas.setVisible(true);
            }
        });


        historialMédicoDeMascotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame historialFrame = new JFrame("Historial Del Veterinario");
                historial historialPanel = new historial(); // Instancia del panel `historial`
                historialFrame.setContentPane(historialPanel.PHistorial);
                historialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                historialFrame.setSize(900, 600);
                historialFrame.setPreferredSize(new Dimension(900, 600));
                historialFrame.setLocationRelativeTo(null);
                historialFrame.pack();
                historialFrame.setVisible(true);


            }
        });

        eliminarCitaYTrataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame eliminarFrame = new JFrame("Eliminar para el Veterinario");
                eliminar eliminarPanel = new eliminar();
                eliminarFrame.setContentPane(eliminarPanel.PEliminarVet);
                eliminarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                eliminarFrame.setSize(900, 600);
                eliminarFrame.setPreferredSize(new Dimension(900, 600));
                eliminarFrame.setLocationRelativeTo(null);
                eliminarFrame.pack();
                eliminarFrame.setVisible(true);

            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });
    }
}
