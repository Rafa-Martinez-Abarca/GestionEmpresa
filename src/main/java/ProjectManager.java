/**
 * Empleado que implementa la capacidad de dirigir definida en Dirigir.
 */
public class ProjectManager extends Empleados implements Dirigir {
    private boolean supervisarAvances;
    private String asignacionTareas;

    public ProjectManager(int id, String nombre, String email, double salario, boolean supervisarAvances,
            String asignacionTareas) {
        super(id, nombre, email, salario);
        this.supervisarAvances = supervisarAvances;
        this.asignacionTareas = asignacionTareas;
    }

    public String getAsignacionTareas() {
        return asignacionTareas;
    }

    public boolean getSupervisionAvances() {
        return supervisarAvances;
    }

    @Override
    public String toString() {
        return super.toString() + " Tareas diarias supervisadas: " + supervisarAvances + " Asignación de tareas: "
                + asignacionTareas;
    }

    /**
     * Muestra por consola una descripción de su función; no modifica proyectos ni empleados.
     */
    @Override
    public void dirigir() {
        System.out.println("El manager se encarga de gestionar la empresa y revisar los avances de los empleados.");
    }
}
