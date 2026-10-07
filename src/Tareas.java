/**
 * Tarea con horas estimadas y reales, estado, prioridad y un responsable opcional.
 */
public class Tareas {
    private int id;
    private String titulo;
    private String descripcion;
    private int horasEstimadas;
    private int horasReales;
    private Prioridad prioridad;
    private EstadoTarea estadoTarea;
    private Empleados empleados;
    private Proyecto proyecto = null;

    /**
     * Crea una tarea pendiente, sin proyecto ni responsable.
     *
     * @throws IllegalArgumentException si el id o las horas reales son negativos,
     *         o si las horas estimadas no son positivas
     * @throws NoPuedeSerNullException si la prioridad es null
     */
    public Tareas(int id, String titulo, String descripcion, int horasEstimadas, int horasReales, Prioridad prioridad) {
        if (id < 0) {
            throw new IllegalArgumentException("No se puede pasar un ID negativo.");
        }
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        if (horasEstimadas <= 0) {
            throw new IllegalArgumentException("Las horas estimadas deben ser mayores que cero.");
        }
        this.horasEstimadas = horasEstimadas;
        if (horasReales < 0) {
            throw new IllegalArgumentException("No puede haber horas negativas.");
        }
        this.horasReales = horasReales;
        if (prioridad == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }
        this.prioridad = prioridad;
        estadoTarea = EstadoTarea.PENDIENTE;

    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getHorasEstimadas() {
        return horasEstimadas;
    }

    public int getHorasReales() {
        return horasReales;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    /**
     * Actualiza la prioridad; rechaza null con NoPuedeSerNullException.
     */
    public void setPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }
        this.prioridad = prioridad;
    }

    public EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }

    /**
     * Valida la transición antes de cambiar el estado. Completada y cancelada son
     * estados finales. Solo se puede completar o volver a pendiente desde en progreso.
     *
     * @throws IllegalArgumentException si el estado es null o la transición no está permitida
     */
    public void setEstadoTarea(EstadoTarea estadoTarea) {
        if (estadoTarea == null) {
            throw new IllegalArgumentException("No se admiten valores nulos.");
        }
        if (this.estadoTarea == EstadoTarea.COMPLETADA || this.estadoTarea == EstadoTarea.CANCELADA) {
            throw new IllegalArgumentException("La tarea ya ha sido completada o cancelada.");
        }

        if (estadoTarea == EstadoTarea.EN_PROGRESO && this.estadoTarea == EstadoTarea.EN_PROGRESO) {
            throw new IllegalArgumentException("El estado ya está en progreso.");
        }
        if (estadoTarea == EstadoTarea.PENDIENTE && this.estadoTarea != EstadoTarea.EN_PROGRESO) {
            throw new IllegalArgumentException("Solo se puede cambiar el estado de progreso a pendiente.");
        }

        if (estadoTarea == EstadoTarea.COMPLETADA && this.estadoTarea != EstadoTarea.EN_PROGRESO) {
            throw new IllegalArgumentException("Solo se puede pasar la tarea de en progreso a completada.");
        }

        this.estadoTarea = estadoTarea;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    /**
     * Asigna el empleado almacenado en el equipo del proyecto, buscándolo por id.
     *
     * @throws NoPuedeSerNullException si el empleado o el proyecto son null
     * @throws IllegalArgumentException si el empleado no pertenece al equipo
     */
    public void setEmpleados(Empleados empleados) {
        if (empleados == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }
        if (proyecto == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");

        }
        this.empleados = proyecto.buscarEmpleadosPorId(empleados.getId());
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Establece el proyecto una sola vez. Proyecto.agregarTareas coordina este vínculo
     * con la incorporación al mapa; este método por sí solo no añade la tarea al mapa.
     *
     * @throws NoPuedeSerNullException si el proyecto recibido es null
     * @throws IllegalArgumentException si la tarea ya tiene proyecto
     */
    void setProyecto(Proyecto proyecto) {
        if (proyecto == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos. ");
        }

        if (this.proyecto != null) {
            throw new IllegalArgumentException("Esta tarea ya pertenece a un proyecto.");
        }
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Título: " + titulo + " Descripción: " + descripcion + " Total de horas: " + horasReales
                + " Prioridad de la tarea: " + prioridad + " Estado de la tarea: " + estadoTarea;
    }

    /**
     * La igualdad depende del id. hashCode usa el mismo dato para que los mapas
     * y conjuntos reconozcan como iguales los objetos con ese identificador.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tareas tareas)) {
            return false;
        }

        return this.id == tareas.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
