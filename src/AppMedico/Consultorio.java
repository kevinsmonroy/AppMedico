package AppMedico;

public class Consultorio {
    private int numero;
    private int piso;

    public Consultorio(int numero, int piso) {
        this.numero = numero;
        this.piso = piso;
    }

    public int getNumero() { return numero; }
    public int getPiso() { return piso; }

    @Override
    public String toString() {
        return "Consultorio [Número=" + numero + ", Piso=" + piso + "]";
    }
}
