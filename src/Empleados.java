/**
 * Base común de los empleados. La identidad se determina por el id, incluso entre subtipos.
 */
public abstract class Empleados {
    private int id;
    private String nombre;
    private String email;
    private double salario;

    /**
     * Inicializa los datos comunes; rechaza id o salario negativos con IllegalArgumentException.
     */
    public Empleados(int id, String nombre, String email, double salario) {
        if (id < 0) {
            throw new IllegalArgumentException("No se puede añadir un ID negativo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        if (salario < 0) {
            throw new IllegalArgumentException("No puede haber salarios negativos.");
        }
        this.salario = salario;
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

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Nombre: " + nombre + " Email: " + email + " Salario: " + salario;
    }

    /**
     * La igualdad depende del id. hashCode usa el mismo dato para que los mapas
     * y conjuntos reconozcan como iguales los objetos con ese identificador.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Empleados empleados)) {
            return false;
        }

        return this.id == empleados.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
