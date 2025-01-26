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

    private JTextField codigoCitaTextField;
    private JTextField cedulaTextField;
    private JCalendar calendario;
    private JComboBox<String> comboBoxHora;
    private JTextField tipoMascotaTextField;
    private JTextField nombreMascotaTextField;
    private JCheckBox sexoMachoCheckBox;
    private JCheckBox sexoHembraCheckBox;
    private JCheckBox chequeoGeneralCheckBox;
    private JCheckBox cirugiaCheckBox;
    private JCheckBox aseoCheckBox;
    private JCheckBox vacunaCheckBox;
    private JTextArea motivoCitaTextArea;
    private JTextField costoTextField;
    private JButton agendarButton;
    private JButton regresarButton;
    private Set<String> citasAgendadas;

    public citas() {
        citasAgendadas = new HashSet<>();
        setTitle("Gestor de Citas Veterinarias");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        agregarEventos();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Campos de entrada
        codigoCitaTextField = new JTextField(20);
        cedulaTextField = new JTextField(20);
        calendario = new JCalendar();
        calendario.setPreferredSize(new Dimension(400, 200));
        String[] horasDisponibles = {
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30"
        };
        comboBoxHora = new JComboBox<>(horasDisponibles);
        tipoMascotaTextField = new JTextField(20);
        nombreMascotaTextField = new JTextField(20);
        sexoMachoCheckBox = new JCheckBox("Macho");
        sexoHembraCheckBox = new JCheckBox("Hembra");
        chequeoGeneralCheckBox = new JCheckBox("Chequeo General");
        cirugiaCheckBox = new JCheckBox("Cirugía");
        aseoCheckBox = new JCheckBox("Aseo");
        vacunaCheckBox = new JCheckBox("Vacuna");
        motivoCitaTextArea = new JTextArea(5, 20);
        costoTextField = new JTextField(20);
        agendarButton = new JButton("Agendar Cita");
        regresarButton = new JButton("Regresar");

        // Agregar componentes al panel
        panel.add(new JLabel("Código de la Cita:"));
        panel.add(codigoCitaTextField);

        panel.add(new JLabel("Cédula del Propietario:"));
        panel.add(cedulaTextField);

        panel.add(new JLabel("Fecha de la Cita:"));
        panel.add(calendario);

        panel.add(new JLabel("Hora de la Cita:"));
        panel.add(comboBoxHora);

        panel.add(new JLabel("Tipo de Mascota:"));
        panel.add(tipoMascotaTextField);

        panel.add(new JLabel("Nombre de la Mascota:"));
        panel.add(nombreMascotaTextField);

        panel.add(new JLabel("Sexo de la Mascota:"));
        JPanel sexoPanel = new JPanel();
        sexoPanel.add(sexoMachoCheckBox);
        sexoPanel.add(sexoHembraCheckBox);
        panel.add(sexoPanel);

        panel.add(new JLabel("Tipo de Servicio:"));
        JPanel servicioPanel = new JPanel();
        servicioPanel.add(chequeoGeneralCheckBox);
        servicioPanel.add(cirugiaCheckBox);
        servicioPanel.add(aseoCheckBox);
        servicioPanel.add(vacunaCheckBox);
        panel.add(servicioPanel);

        panel.add(new JLabel("Motivo de la Cita:"));
        panel.add(new JScrollPane(motivoCitaTextArea));

        panel.add(new JLabel("Costo de la Cita:"));
        panel.add(costoTextField);

        panel.add(agendarButton);
        panel.add(regresarButton);

        add(panel);
    }

    private void agregarEventos() {
        // Evento para el botón Agendar
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoCita = codigoCitaTextField.getText().trim();
                String cedula = cedulaTextField.getText().trim();
                String fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd").format(calendario.getDate());
                String horaSeleccionada = (String) comboBoxHora.getSelectedItem();
                String tipoMascota = tipoMascotaTextField.getText().trim();
                String nombreMascota = nombreMascotaTextField.getText().trim();
                String sexoMascota = sexoMachoCheckBox.isSelected() ? "Macho" : sexoHembraCheckBox.isSelected() ? "Hembra" : "";
                String motivoCita = motivoCitaTextArea.getText().trim();
                String costo = costoTextField.getText().trim();

                StringBuilder serviciosSeleccionados = new StringBuilder();
                if (chequeoGeneralCheckBox.isSelected()) serviciosSeleccionados.append("Chequeo General, ");
                if (cirugiaCheckBox.isSelected()) serviciosSeleccionados.append("Cirugía, ");
                if (aseoCheckBox.isSelected()) serviciosSeleccionados.append("Aseo, ");
                if (vacunaCheckBox.isSelected()) serviciosSeleccionados.append("Vacuna, ");

                if (!serviciosSeleccionados.isEmpty()) {
                    serviciosSeleccionados.setLength(serviciosSeleccionados.length() - 2); // Eliminar la ", " final
                }

                if (codigoCita.isEmpty() || cedula.isEmpty() || tipoMascota.isEmpty() || nombreMascota.isEmpty() ||
                        sexoMascota.isEmpty() || motivoCita.isEmpty() || costo.isEmpty() || horaSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                String cita = fechaSeleccionada + " " + horaSeleccionada;

                if (citasAgendadas.contains(cita)) {
                    JOptionPane.showMessageDialog(null, "La cita para esa fecha y hora ya está agendada.");
                } else {
                    citasAgendadas.add(cita);
                    JOptionPane.showMessageDialog(null, "Cita agendada exitosamente:\n"
                            + "Código: " + codigoCita + "\n"
                            + "Fecha: " + fechaSeleccionada + "\n"
                            + "Hora: " + horaSeleccionada + "\n"
                            + "Tipo de Mascota: " + tipoMascota + "\n"
                            + "Nombre de Mascota: " + nombreMascota + "\n"
                            + "Sexo: " + sexoMascota + "\n"
                            + "Servicios: " + serviciosSeleccionados + "\n"
                            + "Motivo: " + motivoCita + "\n"
                            + "Costo: " + costo);
                }
            }
        });

        // Evento para el botón Regresar
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Asegurar selección única en los checkboxes de sexo
        ActionListener sexoListener = e -> {
            if (e.getSource() == sexoMachoCheckBox && sexoMachoCheckBox.isSelected()) {
                sexoHembraCheckBox.setSelected(false);
            } else if (e.getSource() == sexoHembraCheckBox && sexoHembraCheckBox.isSelected()) {
                sexoMachoCheckBox.setSelected(false);
            }
        };
        sexoMachoCheckBox.addActionListener(sexoListener);
        sexoHembraCheckBox.addActionListener(sexoListener);

        // Asegurar selección única en los checkboxes de servicios
        ActionListener servicioListener = e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            if (source.isSelected()) {
                chequeoGeneralCheckBox.setSelected(source == chequeoGeneralCheckBox);
                cirugiaCheckBox.setSelected(source == cirugiaCheckBox);
                aseoCheckBox.setSelected(source == aseoCheckBox);
                vacunaCheckBox.setSelected(source == vacunaCheckBox);
            }
        };
        chequeoGeneralCheckBox.addActionListener(servicioListener);
        cirugiaCheckBox.addActionListener(servicioListener);
        aseoCheckBox.addActionListener(servicioListener);
        vacunaCheckBox.addActionListener(servicioListener);
    }
}
