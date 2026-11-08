/**
 * Cliente de la empresa. Sus proyectos se relacionan con él desde Proyecto.
 */
public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String nombreEmpresa;

    /**
     * Crea el cliente; rechaza un id negativo con IllegalArgumentException.
     */
    public Cliente(int id, String nombre, String email, String nombreEmpresa) {
        if (id < 0) {
            throw new IllegalArgumentException("No se puede pasar un ID negativo.");
        }

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.nombreEmpresa = nombreEmpresa;

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Nombre: " + nombre + " email: " + email + " Nombre de la empresa: " + nombreEmpresa;
    }

    /**
     * La igualdad depende del id. hashCode usa el mismo dato para que los mapas
     * y conjuntos reconozcan como iguales los objetos con ese identificador.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cliente cliente)) {
            return false;
        }

        return this.id == cliente.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
