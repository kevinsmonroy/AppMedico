package AppMedico;

import javax.swing.*;
import java.awt.*;

public class VentanaMedico extends JFrame {
    private Medico medico;
    private GestorCitas gestor;
    private DefaultListModel<Cita> modeloLista;
    private JList<Cita> listaCitas;

    public VentanaMedico(Medico medico, GestorCitas gestor) {
        this.medico = medico;
        this.gestor = gestor;

        setTitle("Panel del Médico - " + medico.getNombre());
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloLista = new DefaultListModel<>();
        listaCitas = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaCitas);

        // Panel de botones → ahora con 4 filas
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnAtender = new JButton("Atender cita");
        JButton btnInasistencia = new JButton("Marcar inasistencia");
        JButton btnCancelar = new JButton("Cancelar cita"); // 🔹 Nuevo
        JButton btnResumen = new JButton("Resumen diario");

        panelBotones.add(btnAtender);
        panelBotones.add(btnInasistencia);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnResumen);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);

        // Eventos
        btnAtender.addActionListener(e -> atenderCita());
        btnInasistencia.addActionListener(e -> marcarInasistencia());
        btnCancelar.addActionListener(e -> cancelarCita()); // 🔹 Nuevo evento
        btnResumen.addActionListener(e -> {
            new ResumenDiario(this.medico, this.gestor).setVisible(true);
        });

        cargarCitas();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarCitas() {
        modeloLista.clear();
        for (Cita c : medico.getAgenda()) {
            modeloLista.addElement(c); // Usa toString() de Cita
        }
    }

    private void atenderCita() {
        Cita seleccionada = listaCitas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita");
            return;
        }
        new AtencionCita(medico, seleccionada);
        cargarCitas();
    }

    private void marcarInasistencia() {
        Cita seleccionada = listaCitas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita");
            return;
        }
        gestor.marcarInasistencia(seleccionada, "Paciente no llegó");
        JOptionPane.showMessageDialog(this, "Inasistencia marcada");
        cargarCitas();
    }

    // 🔹 Nuevo método
    private void cancelarCita() {
        Cita seleccionada = listaCitas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita para cancelar");
            return;
        }

        String motivo = JOptionPane.showInputDialog(this, "Ingrese el motivo de cancelación:");
        if (motivo == null || motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un motivo para cancelar la cita.");
            return;
        }

        gestor.cancelarCita(seleccionada, motivo);
        JOptionPane.showMessageDialog(this, "Cita cancelada con éxito.");
        cargarCitas();
    }
}










