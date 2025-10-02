package AppMedico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;


public class GestorCitas implements Serializable {
    private List<Cita> citas;

    public GestorCitas() {
        this.citas = new ArrayList<>();
    }

    // 1. Programar nueva cita (valida disponibilidad, rango horario y especialidad)
    public void programarCita(Paciente paciente, Medico medico, String fecha, String hora) {

        // Validar rango horario (07:00 - 19:20, última cita a esa hora)
        if (!horaValida(hora)) {
            throw new IllegalArgumentException("La hora de la cita debe estar entre 07:00 y 19:20.");
        }


        // Validar rango horario (07:00 - 19:20, última cita a esa hora)
        if (!horaValida(hora)) {
            throw new IllegalArgumentException("La hora de la cita debe estar entre 07:00 y 19:20.");
        }

        // Validar que el paciente no tenga otra cita con la misma especialidad ese día
        for (Cita c : citas) {
            if (c.obtenerPaciente().equals(paciente) &&
                    c.obtenerFecha().equals(fecha) &&
                    c.obtenerMedico().getEspecialidad() == medico.getEspecialidad()) {
                throw new IllegalArgumentException("El paciente ya tiene una cita con esta especialidad en la misma fecha.");
            }
        }
        // Validar que no se crucen horarios con citas existentes del paciente
        for (Cita c : citas) {
            if (c.obtenerPaciente().equals(paciente) &&
                    c.obtenerFecha().equals(fecha)) {
                if (seCruzanHorarios(c.getHora(), hora)) {
                    throw new IllegalArgumentException("El paciente ya tiene una cita que se cruza con este horario.");
                }
            }
        }

        // Crear y validar agenda
        Cita nuevaCita = new Cita(paciente, medico, fecha, hora);

        boolean addedToAgenda = medico.agregarCitaAgenda(nuevaCita);
        if (!addedToAgenda) {
            throw new IllegalArgumentException("El médico no está disponible en esa fecha/hora.");
        }

        citas.add(nuevaCita);
        enviarConfirmacion(nuevaCita); // simulado
    }

    // 2. Cancelar cita
    public boolean cancelarCita(Cita cita, String motivo) {
        if (cita == null) return false;

        cita.setMotivoCancelacion(motivo);
        cita.setEstado(EstadoCita.CANCELADA);

        Medico med = cita.obtenerMedico();
        if (med != null) {
            med.quitarCitaAgenda(cita); // la quitamos de la agenda del médico
        }

        // 🔹 Ya no removemos la cita de la lista general
        // return citas.remove(cita);

        return true; // confirmamos que se canceló correctamente
    }


    // 3. Reasignar cita
    public Cita reasignarCita(Cita cita, String nuevaFecha, String nuevaHora) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no existe.");
        }

        // Validar que la nueva fecha sea posterior
        if (!(nuevaFecha.compareTo(cita.obtenerFecha()) > 0)) {
            throw new IllegalArgumentException("La nueva fecha debe ser posterior a la original.");
        }

        // Validar que la nueva hora esté en el rango permitido
        if (!horaValida(nuevaHora)) {
            throw new IllegalArgumentException("La hora de la cita debe estar entre 07:00 y 19:20.");
        }

        Medico med = cita.obtenerMedico();
        if (med == null) {
            throw new IllegalArgumentException("Cita sin médico asignado.");
        }

        // Crear una nueva cita temporal para verificar disponibilidad
        Cita nuevaCita = new Cita(cita.obtenerPaciente(), med, nuevaFecha, nuevaHora);

        boolean disponible = med.agregarCitaAgenda(nuevaCita);
        if (!disponible) {
            throw new IllegalArgumentException("El médico no está disponible en la nueva fecha/hora.");
        }

        // Marcar la cita original como cancelada
        cita.setEstado(EstadoCita.CANCELADA);
        cita.setMotivoCancelacion("Reasignada a " + nuevaFecha + " " + nuevaHora);

        // Quitar de la lista general y del médico
        med.quitarCitaAgenda(cita);
        citas.remove(cita);

        // Agregar la nueva cita
        citas.add(nuevaCita);

        return nuevaCita;
    }

    // 4. Listar citas por fecha
    public List<Cita> listarCitasPorFecha(String fecha) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.obtenerFecha().equals(fecha)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // 5. Marcar inasistencia
    public void marcarInasistencia(Cita cita, String motivo) {
        cita.marcarInasistencia();
        cita.setDescripcionAtencion(motivo);
    }


    // Extra: ver todas las citas
    public List<Cita> getTodasLasCitas() {
        return citas;
    }

    // Método simulado
    private void enviarConfirmacion(Cita cita) {
        System.out.println("Confirmación enviada al paciente " + cita.obtenerPaciente().getNombre());
    }

    // Validar rango horario permitido
    private boolean horaValida(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inicio = LocalTime.of(7, 0);
        LocalTime fin = LocalTime.of(19, 20);
        LocalTime horaCita = LocalTime.parse(hora, formatter);

        // La cita debe iniciar dentro del rango permitido
        return !horaCita.isBefore(inicio) && !horaCita.isAfter(fin);
    }
    private boolean seCruzanHorarios(String horaExistente, String nuevaHora) {
        // Convertir horas en minutos desde medianoche
        int existente = convertirHoraAMinutos(horaExistente);
        int nueva = convertirHoraAMinutos(nuevaHora);

        // Cada cita dura 40 min -> verificar solapamiento
        int finExistente = existente + 40;
        int finNueva = nueva + 40;

        return (nueva < finExistente && existente < finNueva);
    }

    // ---- Convertir HH:mm a minutos ----
    private int convertirHoraAMinutos(String hora) {
        String[] partes = hora.split(":");
        int h = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        return h * 60 + m;
    }
    public List<Cita> obtenerCitasPorFechaYMedico(LocalDate fecha, Medico medico) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getMedico().equals(medico) &&
                    c.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    public void atenderCita(Cita cita, String descripcion, String examenes, String formula) {
        cita.setDescripcionAtencion(descripcion);
        cita.setExamenesRecomendados(examenes);
        cita.setFormulaMedica(formula);
        cita.setEstado(EstadoCita.ATENDIDA);

        // Enviar correo real al paciente
        CorreoService correo = new CorreoService();
        String mensaje = "Hola " + cita.getPaciente().obtenerNombreCompleto() + ",\n\n" +
                "Detalles de su cita:\n" +
                "Descripción: " + descripcion + "\n" +
                "Exámenes: " + examenes + "\n" +
                "Fórmula: " + formula + "\n\n" +
                "Atendido por: " + cita.getMedico().obtenerNombreCompleto();

        correo.enviarCorreo(cita.getPaciente().getCorreo(),
                "Resultado de su cita médica",
                mensaje);

        System.out.println("Cita cerrada y correo enviado.");
    }

    public void mostrarCitasPorFechaConEdad(String fecha) {
        List<Cita> citasDelDia = listarCitasPorFecha(fecha);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Citas para el día " + fecha + ":");
        for (Cita c : citasDelDia) {
            String fn = c.getPaciente().getFechaNacimiento();
            LocalDate fechaNac = LocalDate.parse(fn, formatter);
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();

            System.out.println("Paciente: " + c.getPaciente().obtenerNombreCompleto() +
                    " | Edad: " + edad +
                    " | Hora: " + c.getHora() +
                    " | Especialidad: " + c.getMedico().getEspecialidad().getNombre());
        }
    }
    public void mostrarReportePorFecha(String fecha) {
        List<Cita> citasDelDia = listarCitasPorFecha(fecha);
        System.out.println("Reporte de citas para el día " + fecha + ":");
        for (Cita c : citasDelDia) {
            EstadoCita estado = c.getEstado();
            if (estado == EstadoCita.ATENDIDA || estado == EstadoCita.INASISTENCIA) {
                System.out.println("Paciente: " + c.getPaciente().obtenerNombreCompleto() +
                        " | Hora: " + c.getHora() +
                        " | Estado: " + estado);
            }
        }
    }


}



