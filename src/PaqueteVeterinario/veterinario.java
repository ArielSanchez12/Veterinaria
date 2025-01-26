package PaqueteVeterinario;

import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class veterinario {
    public JPanel PVeterinario;
    public JButton gestiónDeCitasButton;
    public JButton diagnosticosYTratamientosButton;
    public JButton historialMédicoDeMascotasButton;
    public JButton salirButton;
    private JButton eliminarCitaYTrataButton;

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

        diagnosticosYTratamientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para Diagnósticos y Tratamientos
            }
        });

        historialMédicoDeMascotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para Historial Médico
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new login();
            }
        });

        eliminarCitaYTrataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
