import java.util.ArrayList;
import java.util.HashMap;

/**
 * Almacena objetos por id y centraliza las validaciones de registro y búsqueda.
 *
 * @param <T> tipo de los objetos almacenados
 */
public class Registro<T> {

    private HashMap<Integer, T> elementos = new HashMap<>();

    /**
     * Registra un elemento sin sustituir otro con el mismo id.
     *
     * @param id clave elegida por quien llama; debe corresponder al objeto
     * @param elemento objeto que se almacena
     * @throws IllegalArgumentException si el id es negativo o está repetido
     * @throws NoPuedeSerNullException si el elemento es null
     */
    public void agregar(int id, T elemento) {
        if (id < 0) {
            throw new IllegalArgumentException("No se admiten valores negativos.");
        }

        if (elemento == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos");
        }

        if (elementos.containsKey(id)) {
            throw new IllegalArgumentException("ID repetido.");
        }

        elementos.put(id, elemento);

    }


    /**
     * Busca un elemento por su clave.
     *
     * @param id identificador que se busca
     * @return el objeto almacenado
     * @throws IllegalArgumentException si el id no existe
     */
    public T buscarPorID(int id){

        

        if (!elementos.containsKey(id)) {
                
           throw new IllegalArgumentException("No existe ese id");
        }

        return elementos.get(id);

    }


    /**
     * Devuelve una lista nueva, sin orden garantizado. Los objetos no se clonan:
     * modificar la lista no modifica el mapa, pero sus elementos son los mismos.
     *
     * @return copia de las referencias almacenadas
     */
    public ArrayList<T> obtenerTodos(){
        ArrayList<T> obtenerTodosLosRegistros = new ArrayList<>();

        for (T t : elementos.values()) {
            obtenerTodosLosRegistros.add(t);
        }

        return obtenerTodosLosRegistros;
    }
    
}
