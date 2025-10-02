package AppMedico;

import javax.swing.*;
import java.awt.*;

public class AtencionCita extends JFrame {
    private Medico medico;
    private Cita cita;

    private JTextArea txtDiagnostico;
    private JTextArea txtExamenes;
    private JTextArea txtFormulas;
    private JButton btnGuardar;

    public AtencionCita(Medico medico, Cita cita) {
        this.medico = medico;
        this.cita = cita;

        setTitle("Atender Cita - " + cita.getPaciente().obtenerNombreCompleto());
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panelCentro = new JPanel(new GridLayout(3, 1, 10, 10));

        txtDiagnostico = new JTextArea("Diagnóstico...");
        txtExamenes = new JTextArea("Exámenes...");
        txtFormulas = new JTextArea("Fórmulas...");

        panelCentro.add(new JScrollPane(txtDiagnostico));
        panelCentro.add(new JScrollPane(txtExamenes));
        panelCentro.add(new JScrollPane(txtFormulas));

        btnGuardar = new JButton("Guardar Atención");

        add(panelCentro, BorderLayout.CENTER);
        add(btnGuardar, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarAtencion());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void guardarAtencion() {
        String diag = txtDiagnostico.getText();
        String exam = txtExamenes.getText();
        String form = txtFormulas.getText();

        medico.atenderCita(cita, diag, exam, form);

        JOptionPane.showMessageDialog(this, "Cita atendida y guardada");
        dispose();
    }
}

