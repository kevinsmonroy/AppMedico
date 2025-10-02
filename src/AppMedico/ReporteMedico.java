package AppMedico;

import java.util.List;

public class ReporteMedico {
    private String fecha;
    private int totalAtendidas;
    private int totalInasistencias;

    public ReporteMedico(String fecha, List<Cita> citas) {
        this.fecha = fecha;
        this.totalAtendidas = 0;
        this.totalInasistencias = 0;

        for (Cita c : citas) {
            if (c.getFecha().equals(fecha)) {
                if (c.getEstado() == EstadoCita.ATENDIDA) {
                    totalAtendidas++;
                } else if (c.getEstado() == EstadoCita.INASISTENCIA) {
                    totalInasistencias++;
                }
            }
        }
    }

    public String getFecha() {
        return fecha;
    }

    public int getTotalAtendidas() {
        return totalAtendidas;
    }

    public int getTotalInasistencias() {
        return totalInasistencias;
    }

    public String generarResumen() {
        return "Reporte del " + fecha +
                " -> Atendidas: " + totalAtendidas +
                ", Inasistencias: " + totalInasistencias;
    }
}

