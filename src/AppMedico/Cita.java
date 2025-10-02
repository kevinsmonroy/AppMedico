package AppMedico;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Cita {
    private Paciente paciente;
    private Medico medico;
    private String fecha;
    private String hora;
    private final int duracion = 40;
    private String motivoCancelacion;
    private String descripcionAtencion;
    private String examenesRecomendados;
    private String formulaMedica;
    private EstadoCita estado;

    public Cita(Paciente paciente, Medico medico, String fecha, String hora) {
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = EstadoCita.PENDIENTE;
    }

    // --- getters "viejos" (si ya los tienes) ---
    public Paciente obtenerPaciente() { return paciente; }
    public Medico obtenerMedico() { return medico; }
    public String obtenerFecha() { return fecha; }
    public String obtenerHora() { return hora; }
    public EstadoCita obtenerEstado() { return estado; }

    // --- getters JavaBean/compatibilidad (para getMedico(), getFecha(), etc.) ---
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public EstadoCita getEstado() { return estado; }

    // --- getters de datos clínicos ---
    public String getDescripcionAtencion() { return descripcionAtencion; }
    public String getExamenesRecomendados() { return examenesRecomendados; }
    public String getFormulaMedica() { return formulaMedica; }
    public String getMotivoCancelacion() { return motivoCancelacion; }

    public LocalDateTime getFechaHora() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate localDate = LocalDate.parse(this.fecha, dateFormatter);
        LocalTime localTime = LocalTime.parse(this.hora, timeFormatter);

        return LocalDateTime.of(localDate, localTime);
    }


    // --- setters ---
    public void setDescripcionAtencion(String descripcionAtencion) {
        this.descripcionAtencion = descripcionAtencion;
    }

    public void setExamenesRecomendados(String examenesRecomendados) {
        this.examenesRecomendados = examenesRecomendados;
    }

    public void setFormulaMedica(String formulaMedica) {
        this.formulaMedica = formulaMedica;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
    public void marcarInasistencia() {
        this.estado = EstadoCita.INASISTENCIA;
    }



    @Override
    public String toString() {
        return "Cita[" + fecha + " " + hora + " | paciente=" +
                (paciente != null ? paciente.obtenerNombreCompleto() : "null") +
                " | medico=" + (medico != null ? medico.obtenerNombreCompleto() : "null") +
                " | estado=" + estado + "]";
    }
}



