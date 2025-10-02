package AppMedico;

import java.io.Serializable;
import java.util.List;

public class ReporteMedico implements Serializable {
    private String fecha;
    private int totalCitas;
    private int totalAtendidas;
    private int totalInasistencias;
    private int totalPendientes;
    private StringBuilder detalles;

    public ReporteMedico(String fecha, List<Cita> citas) {
        this.fecha = fecha;
        this.totalCitas = 0;
        this.totalAtendidas = 0;
        this.totalInasistencias = 0;
        this.totalPendientes = 0;
        this.detalles = new StringBuilder();

        for (Cita c : citas) {
            if (c.getFecha().equals(fecha)) {
                totalCitas++;
                detalles.append(formatoCita(c)).append("\n");
                if (c.getEstado() == EstadoCita.ATENDIDA) {
                    totalAtendidas++;
                } else if (c.getEstado() == EstadoCita.INASISTENCIA) {
                    totalInasistencias++;
                } else if (c.getEstado() == EstadoCita.PENDIENTE) {
                    totalPendientes++;
                }
            }
        }
    }

    // Puedes personalizar este método para mostrar los datos que quieras de cada cita
    private String formatoCita(Cita c) {
        String nombrePaciente = c.getPaciente().obtenerNombreCompleto();
        String hora = c.getHora();
        String especialidad = c.getMedico().getEspecialidad().getNombre();
        String estado = c.getEstado().toString();
        return String.format("Paciente: %-18s | Hora: %-5s | Especialidad: %-12s | Estado: %s",
                nombrePaciente, hora, especialidad, estado);
    }

    public String getFecha() {
        return fecha;
    }

    public int getTotalCitas() {
        return totalCitas;
    }

    public int getTotalAtendidas() {
        return totalAtendidas;
    }

    public int getTotalInasistencias() {
        return totalInasistencias;
    }

    public int getTotalPendientes() {
        return totalPendientes;
    }

    public String generarResumen() {
        return "Reporte del " + fecha +
                "\nTotal citas: " + totalCitas +
                "\n - Atendidas: " + totalAtendidas +
                "\n - Inasistencias: " + totalInasistencias +
                "\n - Pendientes: " + totalPendientes +
                "\n\nCitas del día:\n" +
                (totalCitas == 0 ? "No hay citas programadas para esta fecha." : detalles.toString());
    }
}

