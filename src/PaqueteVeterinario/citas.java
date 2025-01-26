package PaqueteVeterinario;

import PaqueteRecursos.conexion;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private JComboBox<String> comboBoxServicio;
    private JTextArea motivoCitaTextArea;
    private JTextField costoTextField;
    private JTextField salaTextField;
    private JButton agendarButton;
    private JButton regresarButton;
    private Set<String> citasAgendadas;

    public citas() {
        citasAgendadas = new HashSet<>();
        setTitle("Gestor de Citas Veterinarias");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        agregarEventos();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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

        // Grupo de botones para los checkboxes de sexo
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(sexoMachoCheckBox);
        grupoSexo.add(sexoHembraCheckBox);

        String[] servicios = {"Chequeo General", "Cirugía", "Aseo", "Vacunación"};
        comboBoxServicio = new JComboBox<>(servicios);
        motivoCitaTextArea = new JTextArea(5, 20);
        costoTextField = new JTextField(20);
        salaTextField = new JTextField(20);
        agendarButton = new JButton("Agendar Cita");
        regresarButton = new JButton("Regresar");

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
        panel.add(comboBoxServicio);

        panel.add(new JLabel("Motivo de la Cita:"));
        panel.add(new JScrollPane(motivoCitaTextArea));

        panel.add(new JLabel("Costo de la Cita:"));
        panel.add(costoTextField);

        panel.add(new JLabel("Sala:"));
        panel.add(salaTextField);

        panel.add(agendarButton);
        panel.add(regresarButton);

        add(panel);
    }

    private void agregarEventos() {
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoCita = codigoCitaTextField.getText().trim();
                String cedula = cedulaTextField.getText().trim();
                String fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd").format(calendario.getDate());
                String horaSeleccionada = (String) comboBoxHora.getSelectedItem();
                String tipoMascota = tipoMascotaTextField.getText().trim();
                String nombreMascota = nombreMascotaTextField.getText().trim();
                String sexoMascota = sexoMachoCheckBox.isSelected() ? "macho" : sexoHembraCheckBox.isSelected() ? "hembra" : "";
                String tipoServicio = (String) comboBoxServicio.getSelectedItem();
                String motivoCita = motivoCitaTextArea.getText().trim();
                String costo = costoTextField.getText().trim();
                String sala = salaTextField.getText().trim();

                if (codigoCita.isEmpty() || cedula.isEmpty() || tipoMascota.isEmpty() || nombreMascota.isEmpty() ||
                        sexoMascota.isEmpty() || motivoCita.isEmpty() || costo.isEmpty() || horaSeleccionada == null || sala.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                // Validar que el costo no sea negativo
                try {
                    double costoDouble = Double.parseDouble(costo);
                    if (costoDouble < 0) {
                        JOptionPane.showMessageDialog(null, "El costo no puede ser un valor negativo.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un costo válido.");
                    return;
                }

                String cita = fechaSeleccionada + " " + horaSeleccionada;

                if (citasAgendadas.contains(cita)) {
                    JOptionPane.showMessageDialog(null, "La cita para esa fecha y hora ya está agendada.");
                } else {
                    try (Connection conn = new conexion().connect()) {
                        String sql = "INSERT INTO citas_veterinarias (codigo_cita, cedula, fecha, hora, tipo_mascota, " +
                                "nombre_mascota, sexo_mascota, tipo_servicio, motivo_cita, costo, sala) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(codigoCita));
                        ps.setString(2, cedula);
                        ps.setString(3, fechaSeleccionada);
                        ps.setString(4, horaSeleccionada);
                        ps.setString(5, tipoMascota);
                        ps.setString(6, nombreMascota);
                        ps.setString(7, sexoMascota);
                        ps.setString(8, tipoServicio);
                        ps.setString(9, motivoCita);
                        ps.setDouble(10, Double.parseDouble(costo));
                        ps.setString(11, sala);

                        ps.executeUpdate();
                        citasAgendadas.add(cita);
                        JOptionPane.showMessageDialog(null, "Cita agendada exitosamente.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos: " + ex.getMessage());
                    }
                }
            }
        });

        regresarButton.addActionListener(e -> dispose());
    }
}
