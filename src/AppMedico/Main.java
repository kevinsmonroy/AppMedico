package AppMedico;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Usuario> usuarios = new ArrayList<>();
            GestorCitas gestor = new GestorCitas();

            // Crear médico
            Medico medico1 = new Medico(
                    "1001", "Juan", "Pérez", "3100000000",
                    "1980-01-01", "juan@correo.com",
                    "Chapinero", "1234", Especialidad.ORTOPEDIA
            );
            usuarios.add(medico1);

            // Crear pacientes
            Paciente pac1 = new Paciente("2001", "María", "Gómez", "3200000000",
                    "1990-05-05", "maria@correo.com", "Suba", "123", "SaludTotal");
            Paciente pac2 = new Paciente("2002", "Carlos", "López", "3001112222",
                    "1985-02-12", "carlos@correo.com", "Usaquén", "456", "EPS Sanitas");
            Paciente pac3 = new Paciente("2003", "Laura", "Martínez", "3003334444",
                    "1993-08-22", "laura@correo.com", "Kennedy", "789", "Compensar");

            usuarios.add(pac1);
            usuarios.add(pac2);
            usuarios.add(pac3);

            // Programar citas de prueba
            gestor.programarCita(pac1, medico1, "2025-10-01", "08:00");
            gestor.programarCita(pac2, medico1, "2025-10-01", "09:00");
            gestor.programarCita(pac3, medico1, "2025-10-01", "10:00");
            gestor.programarCita(pac1, medico1, "2025-10-02", "08:30");
            gestor.programarCita(pac2, medico1, "2025-10-02", "09:30");
            gestor.programarCita(pac3, medico1, "2025-10-03", "11:00");

            // Marcar estados de algunas citas
            gestor.getTodasLasCitas().get(1).setEstado(EstadoCita.ATENDIDA);      // c2
            gestor.getTodasLasCitas().get(2).setEstado(EstadoCita.INASISTENCIA);  // c3

            // Mostrar info de usuarios
            for (Usuario u : usuarios) {
                System.out.println(u.mostrarInfo());
            }

            // Mostrar listado de citas por fecha con edad del paciente (RQ7)
            gestor.mostrarCitasPorFechaConEdad("2025-10-01");

            // Abrir login (si ya tienes clase Login implementada)
            Login login = new Login(usuarios, gestor);
            login.setVisible(true);
        });
    }
}






