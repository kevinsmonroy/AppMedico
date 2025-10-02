package AppMedico;

public enum Especialidad {
    CARDIOLOGIA("Cardiología"),
    PEDIATRIA("Pediatría"),
    NEUROLOGIA("Neurología"),
    DERMATOLOGIA("Dermatología"),
    GINECOLOGIA("Ginecología"),
    OFTALMOLOGIA("Oftalmología"),
    ORTOPEDIA("Ortopedia"),
    PSIQUIATRIA("Psiquiatría"),
    UROLOGIA("Urología"),
    MEDICINA_INTERNA("Medicina Interna");

    private final String nombre;

    Especialidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

