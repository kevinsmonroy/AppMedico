package AppMedico;

import java.io.*;
import java.util.List;

public class PersistenciaBinaria {

    // Guarda una lista de objetos en binario
    public static <T extends Serializable> void guardarLista(List<T> lista, String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
            System.out.println("Datos guardados correctamente en " + archivo);
        } catch (IOException e) {
            System.err.println("Error guardando datos: " + e.getMessage());
        }
    }

    // Carga una lista de objetos desde binario
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> cargarLista(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando datos: " + e.getMessage());
            return null;
        }
    }

    // Guarda cualquier objeto en binario
    public static <T extends Serializable> void guardarObjeto(T objeto, String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(objeto);
            System.out.println("Objeto guardado correctamente en " + archivo);
        } catch (IOException e) {
            System.err.println("Error guardando objeto: " + e.getMessage());
        }
    }

    // Carga cualquier objeto desde binario
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T cargarObjeto(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando objeto: " + e.getMessage());
            return null;
        }
    }
}
