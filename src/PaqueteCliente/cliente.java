package PaqueteCliente;

import PaqueteRecursos.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class cliente {
    public JPanel PCliente;
    public JButton agendarCitaButton;
    public JButton historialMédicoDeMiButton;
    public JButton salirButton;
    private JButton eliminarRegistroButton;

    public cliente() {

        JFrame frame = new JFrame("Cliente");
        frame.setContentPane(PCliente);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/PaqueteRecursos/iconos/perfil-cliente.png").getImage());
        frame.pack();
        frame.setVisible(true);


        agendarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarMascota();
                frame.dispose();
            }
        });

        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarRegistro();
                frame.dispose();
            }
        });


        historialMédicoDeMiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new historialMedico();
                frame.dispose();
            }
        });


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new login();
            }
        });
    }
}