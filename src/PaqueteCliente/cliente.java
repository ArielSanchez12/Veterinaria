package PaqueteCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class cliente {
    public JPanel PCliente;
    public JButton agendarCitaButton;
    public JButton historialMédicoDeMiButton;
    public JButton salirButton;

    public cliente() {

        JFrame frame = new JFrame("Cliente");
        frame.setContentPane(PCliente);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        agendarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Agendar Citas");
                frame.setContentPane(new agendarCitas().PAgendar);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 300);
                frame.setPreferredSize(new Dimension(900, 500));
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            }
        });


        historialMédicoDeMiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Historial Medico de la Mascota");
                frame.setContentPane(new historialMedico().PHistorial);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 300);
                frame.setPreferredSize(new Dimension(900, 500));
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
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