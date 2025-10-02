
package AppMedico;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ResumenDiario extends JFrame implements Serializable {
    private Medico medico;
    private GestorCitas gestor;
    private JTextArea area;
    private JSpinner spFecha;

    public ResumenDiario(Medico medico, GestorCitas gestor) {
        this.medico = medico;
        this.gestor = gestor;

        setTitle("Resumen Diario");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con selector de fecha
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.add(new JLabel("Seleccione la fecha:"));

        spFecha = new JSpinner(new SpinnerDateModel());
        spFecha.setEditor(new JSpinner.DateEditor(spFecha, "yyyy-MM-dd"));
        panelSuperior.add(spFecha);

        JButton btnGenerar = new JButton("Generar Resumen");
        panelSuperior.add(btnGenerar);

        add(panelSuperior, BorderLayout.NORTH);

        // Área de texto para mostrar el reporte
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón
        btnGenerar.addActionListener(e -> generarResumen());

        setVisible(true);
    }

    private void generarResumen() {
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(spFecha.getValue());
        ReporteMedico reporte = new ReporteMedico(fecha, gestor.getTodasLasCitas());

        area.setText(""); // limpiar antes
        area.append("=== Reporte del " + fecha + " ===\n\n");
        area.append(reporte.generarResumen());
    }
}




