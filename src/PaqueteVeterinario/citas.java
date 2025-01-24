package PaqueteVeterinario;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class citas extends JFrame {

        private JButton agendarButton;
        private JTextField textField1;
        private JTextField textField2;
        private JComboBox<String> comboBoxHora;  // JComboBox para seleccionar la hora

        private Set<String> citasAgendadas;

        public citas() {
            citasAgendadas = new HashSet<>();

            setTitle("Gestor de Citas Veterinarias");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JCalendar calendario = new JCalendar();
            calendario.setPreferredSize(new Dimension(300, 200));

            textField1 = new JTextField(20);
            textField2 = new JTextField(20);

            String[] horasDisponibles = {
                    "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                    "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30"
            };
            comboBoxHora = new JComboBox<>(horasDisponibles);

            agendarButton = new JButton("Agendar Cita");

            agendarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textField1.getText().isEmpty() || textField2.getText().isEmpty() || comboBoxHora.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    } else if (textField1.getText().trim().isEmpty() || textField2.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Los nombres no pueden ser solo espacios.");
                    } else {
                        String fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd").format(calendario.getDate());
                        String horaSeleccionada = (String) comboBoxHora.getSelectedItem();

                        String cita = fechaSeleccionada + " " + horaSeleccionada;

                        if (citasAgendadas.contains(cita)) {
                            JOptionPane.showMessageDialog(null, "La cita para esa fecha y hora ya está agendada. Por favor, elija otro día u horario.");
                        } else {
                            citasAgendadas.add(cita);
                            JOptionPane.showMessageDialog(null, "Cita agendada exitosamente para: " + fechaSeleccionada + " a las " + horaSeleccionada);
                        }
                    }
                }
            });

            JPanel panel = new JPanel();
            panel.add(calendario);
            panel.add(new JLabel("Nombre de la mascota:"));
            panel.add(textField1);
            panel.add(new JLabel("Nombre del propietario:"));
            panel.add(textField2);
            panel.add(new JLabel("Hora:"));
            panel.add(comboBoxHora);
            panel.add(agendarButton);

            add(panel);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new citas().setVisible(true);
                }
            });
        }
    }
