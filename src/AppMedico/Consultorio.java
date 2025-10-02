package AppMedico;

import java.io.Serializable;

public class Consultorio implements Serializable {
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
