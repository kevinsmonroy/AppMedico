package AppMedico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Medico extends Usuario  implements Serializable {
    private Especialidad especialidad;
    private List<Cita> agenda; // lista de citas asignadas al médico

    public Medico(String cedula, String nombre, String apellido, String telefono,
                  String fechaNacimiento, String correo, String contrasena,
                  String barrio,Especialidad especialidad) {
        super(cedula, nombre, apellido, telefono, fechaNacimiento, correo, barrio,contrasena);
        this.especialidad = especialidad;
        this.agenda = new ArrayList<>();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    // Agrega una cita a la agenda si no hay conflicto (misma fecha y hora)
    // Devuelve true si fue añadida correctamente, false si hay conflicto
    public boolean agregarCitaAgenda(Cita cita) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inicioNueva = LocalTime.parse(cita.getHora(), formatter);
        LocalTime finNueva = inicioNueva.plusMinutes(40);

        for (Cita c : agenda) {
            LocalTime inicioExistente = LocalTime.parse(c.getHora(), formatter);
            LocalTime finExistente = inicioExistente.plusMinutes(40);

            boolean traslape = !finNueva.isBefore(inicioExistente) && !inicioNueva.isAfter(finExistente);

            if (c.getFecha().equals(cita.getFecha()) && traslape) {
                return false; // conflicto de horario
            }
        }

        agenda.add(cita);
        return true;
    }


    // Quita una cita de la agenda (si existe)
    public void quitarCitaAgenda(Cita cita) {
        agenda.remove(cita);
    }

    // Acceso a la agenda (si lo necesitas)
    public List<Cita> getAgenda() {
        return new ArrayList<>(agenda);
    }

    // Ejemplo: atender cita (usa los setters de Cita)
    public void atenderCita(Cita cita, String descripcion, String examenes, String formula) {
        cita.setDescripcionAtencion(descripcion);
        cita.setExamenesRecomendados(examenes);
        cita.setFormulaMedica(formula);
        cita.setEstado(EstadoCita.ATENDIDA);
        // opcional: quitar de agenda si quieres
        // this.quitarCitaAgenda(cita);
    }

    // Registrar inasistencia
    public void registrarInasistencia(Cita cita, String observaciones) {
        cita.setEstado(EstadoCita.INASISTENCIA);
        cita.setDescripcionAtencion(observaciones);
        System.out.println("Inasistencia registrada: " + observaciones);
    }


    @Override
    public String mostrarInfo() {
        String esp = (especialidad != null ? especialidad.getNombre() : "Sin especialidad");
        return "Médico: " + getNombre() + " " + getApellido() +
                " | Especialidad: " + esp +
                " | Correo: " + getCorreo();
    }
}

