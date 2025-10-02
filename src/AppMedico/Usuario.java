package AppMedico;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    private String cedula;
    private String nombre;
    private String apellido;
    private String barrio;
    private String telefono;
    private String fechaNacimiento;
    private String correo;
    private String contrasena;

    public Usuario(String cedula, String nombre, String apellido,
                   String telefono, String fechaNacimiento,
                   String correo, String barrio, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.barrio = barrio;
        this.contrasena = contrasena;
    }

    // Getters
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getBarrio(){ return barrio;}
    public String getTelefono() { return telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }

    // No todos los atributos necesitan setters
    public void setBarrio(String barrio){ this.barrio = barrio;}
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // Métodos de utilidad
    public String obtenerNombreCompleto() {
        return nombre + " " + apellido;
    }
    public abstract String mostrarInfo();
    public boolean autenticar(String id, String password) {
        return this.cedula.equals(id) && this.contrasena.equals(password);
    }

}

