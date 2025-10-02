package AppMedico;

public class Paciente extends Usuario {
    private String eps;

    public Paciente(String cedula, String nombre, String apellido, String telefono,
                    String fechaNacimiento, String correo, String barrio, String contrasena,
                    String eps) {
        super(cedula, nombre, apellido, telefono, fechaNacimiento, correo, barrio, contrasena);
        this.eps = eps;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    @Override
    public String mostrarInfo() {
        return "Paciente: " + getNombre() + " " + getApellido() +
                " | EPS: " + eps +
                " | Correo: " + getCorreo();
    }
}

