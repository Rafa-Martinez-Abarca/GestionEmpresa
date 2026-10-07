
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Proyecto de un cliente que administra su equipo, sus tareas y sus reglas de negocio.
 */
public class Proyecto {
    private int id;
    private String nombreProyecto;
    private EstadoProyecto estadoProyecto;
    private double costeTotal;
    private Cliente cliente;
    private HashMap<Integer, Tareas> listaTareas = new HashMap<>();
    private HashMap<Integer, Empleados> listaEmpleados = new HashMap<>();

    /**
     * Crea un proyecto en planificación. Rechaza id o presupuesto negativos y cliente null.
     */
    public Proyecto(int id, String nombreProyecto, double costeTotal, Cliente cliente) {
        if (id < 0) {
            throw new IllegalArgumentException("No se puede pasar un ID negativo.");
        }

        this.id = id;
        this.nombreProyecto = nombreProyecto;

        if (costeTotal < 0) {
            throw new IllegalArgumentException("Un proyecto no puede tener un precio negativo.");
        }
        this.costeTotal = costeTotal;

        estadoProyecto = EstadoProyecto.PLANIFICACION;

        if (cliente == null) {
            throw new NoPuedeSerNullException("No se admiten datos nulos.");
        }

        this.cliente = cliente;

    }

    public int getId() {
        return id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    /**
     * Cambia el estado si el proyecto no está cerrado. Para finalizarlo, no puede
     * haber tareas pendientes ni en progreso. Cancelarlo no cambia los estados de sus tareas.
     *
     * @throws IllegalArgumentException si el estado es null o el cambio no está permitido
     */
    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {

        if (estadoProyecto == null) {
            throw new IllegalArgumentException("No se pueden pasar valores nulos.");
        }
        if (this.estadoProyecto == EstadoProyecto.CANCELADO) {
            throw new IllegalArgumentException("Proyecto cancelado, no se puede modificar un estado cancelado. ");
        }

        if (this.estadoProyecto == EstadoProyecto.FINALIZADO) {
            throw new IllegalArgumentException("Proyecto finalizado, no se puede modificar.");
        }

        for (Tareas tareas : listaTareas.values()) {
            if (estadoProyecto == EstadoProyecto.FINALIZADO && (tareas.getEstadoTarea() == EstadoTarea.PENDIENTE
                    || tareas.getEstadoTarea() == EstadoTarea.EN_PROGRESO)) {
                throw new IllegalArgumentException("No se puede finalizar un proyecto con tareas sin completar. ");
            }
        }
        this.estadoProyecto = estadoProyecto;
    }

    public double getCosteTotal() {
        return costeTotal;
    }

    public int totalEmpleados() {

        return listaEmpleados.size();
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return " ID: " + id + " Nombre del proyecto: " + nombreProyecto + " Cliente: " + cliente;
    }

    /**
     * La igualdad depende del id. hashCode usa el mismo dato para que los mapas
     * y conjuntos reconozcan como iguales los objetos con ese identificador.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Proyecto proyecto)) {
            return false;
        }

        return this.id == proyecto.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Añade una tarea sin proyecto y establece su vínculo con este proyecto.
     *
     * @throws ProyectoCerrado si el proyecto está finalizado o cancelado
     * @throws NoPuedeSerNullException si la tarea es null
     * @throws IllegalArgumentException si el id se repite o la tarea ya tiene proyecto
     */
    public void agregarTareas(Tareas tareas) throws ProyectoCerrado {

        if (tareas == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos. ");
        }

        if (listaTareas.containsKey(tareas.getId())) {
            throw new IllegalArgumentException("ID repetido.");
        }

        if (this.estadoProyecto == EstadoProyecto.CANCELADO || this.estadoProyecto == EstadoProyecto.FINALIZADO) {
            throw new ProyectoCerrado("No se puede agregar una tarea si el proyecto ha sido cancelado o finalizado.");
        }

        if (tareas.getProyecto() == null) {
            tareas.setProyecto(this);
            listaTareas.put(tareas.getId(), tareas);

        } else {
            throw new IllegalArgumentException("La tarea ya tiene un proyecto asignado. ");
        }

    }

    /**
     * Añade al equipo un empleado no nulo cuyo id no esté repetido.
     * Se utiliza desde Gestion para comprobar antes su registro en la empresa.
     * El acceso de paquete también permite llamadas desde otras clases del mismo paquete.
     */
    void agregarEmpleados(Empleados empleados) {
        if (empleados == null) {
            throw new NoPuedeSerNullException("No se permiten valores nulos.");
        }
        if (listaEmpleados.containsKey(empleados.getId())) {
            throw new IllegalArgumentException("ID repetido.");
        }

        listaEmpleados.put(empleados.getId(), empleados);

    }

    /**
     * Devuelve el empleado del equipo; lanza IllegalArgumentException si no pertenece a él.
     */
    public Empleados buscarEmpleadosPorId(int id) {
        if (!listaEmpleados.containsKey(id)) {
            throw new IllegalArgumentException("Ese id no corresponde a ningún empleado.");
        }
        return listaEmpleados.get(id);
    }

    /**
     * Asigna usando los objetos guardados en este proyecto, buscados por id.
     *
     * @throws NoPuedeSerNullException si algún argumento es null
     * @throws IllegalArgumentException si la tarea o el empleado no pertenecen al proyecto
     */
    public void asignarTareasEmpleados(Empleados empleados, Tareas tareas) {

        if (empleados == null || tareas == null) {
            throw new NoPuedeSerNullException("No se pueden pasar valores nulos.");
        }
        if (listaEmpleados.containsKey(empleados.getId()) && listaTareas.containsKey(tareas.getId())) {

            Empleados empleadosRegistrados = listaEmpleados.get(empleados.getId());
            Tareas tareasRegistradas = listaTareas.get(tareas.getId());
            tareasRegistradas.setEmpleados(empleadosRegistrados);
        } else {
            throw new IllegalArgumentException("El empleado o la tarea no se encuentran en el proyecto.");
        }

    }

    /**
     * Retira al empleado del equipo si no tiene tareas pendientes o en progreso.
     * Las tareas completadas o canceladas conservan su responsable como historial.
     *
     * @throws NoPuedeSerNullException si el empleado es null
     * @throws IllegalArgumentException si no pertenece al equipo o tiene trabajo sin terminar
     */
    public void eliminarEmpleado(Empleados empleados) {
        if (empleados == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }

        if (listaEmpleados.containsKey(empleados.getId())) {
            for (Tareas tareas : listaTareas.values()) {
                if (empleados.equals(tareas.getEmpleados()) && (tareas.getEstadoTarea() == EstadoTarea.EN_PROGRESO
                        || tareas.getEstadoTarea() == EstadoTarea.PENDIENTE)) {
                    throw new IllegalArgumentException(
                            "Este empleado tiene tareas en progreso o pendientes, no puede ser eliminado del proyecto.");
                }
            }
            listaEmpleados.remove(empleados.getId());

        } else {
            throw new IllegalArgumentException("Ese empleado no se encuentra en este proyecto.");
        }
    }

    /**
     * Devuelve una lista nueva de tareas con ese estado, vacía si no hay coincidencias.
     * Rechaza un estado null.
     */
    public ArrayList<Tareas> obtenerTareasPorEstado(EstadoTarea estadoTarea) {
        ArrayList<Tareas> tareasDeCadaProyecto = new ArrayList<>();
        if (estadoTarea == null) {
            throw new NoPuedeSerNullException("No se pueden pasar valores nulos.");
        }
        for (Tareas tareasProyecto : listaTareas.values()) {
            if (tareasProyecto.getEstadoTarea() == estadoTarea) {
                tareasDeCadaProyecto.add(tareasProyecto);
            }
        }
        return tareasDeCadaProyecto;

    }

    /**
     * Suma las horas reales de todas las tareas, sin filtrar su estado.
     */
    public int calcularHorasTotales() {
        int totalHorasProyecto = 0;

        for (Tareas tareas : listaTareas.values()) {
            totalHorasProyecto += tareas.getHorasReales();

        }
        return totalHorasProyecto;
    }

    /**
     * Devuelve un mapa nuevo con los recuentos por prioridad. Omite prioridades sin tareas.
     */
    public HashMap<Prioridad, Integer> mostrarTareasPorPrioridadDeTareas() {
        HashMap<Prioridad, Integer> proyectoConPrioridad = new HashMap<>();
        for (Tareas tareas : listaTareas.values()) {

            proyectoConPrioridad.put(tareas.getPrioridad(),
                    proyectoConPrioridad.getOrDefault(tareas.getPrioridad(), 0) + 1);

        }
        return proyectoConPrioridad;
    }

    /**
     * Devuelve una lista nueva con los miembros del equipo; los empleados no se clonan.
     */
    public ArrayList<Empleados> obtenerEmpleadosPorProyecto() {
        ArrayList<Empleados> empleadosPorProyecto = new ArrayList<>();
        for (Empleados empleados : listaEmpleados.values()) {
            empleadosPorProyecto.add(empleados);
        }
        return empleadosPorProyecto;
    }

    /**
     * Devuelve las tareas de este proyecto asignadas al empleado, comparado por id.
     * Rechaza null. Las tareas sin responsable no se incluyen.
     */
    public ArrayList<Tareas> obtenerTareasDeEmpleado(Empleados empleados) {
        if (empleados == null) {
            throw new IllegalArgumentException("No se admiten valores nulos.");
        }
        ArrayList<Tareas> tareasEmpleado = new ArrayList<>();
        for (Tareas tareas : listaTareas.values()) {
            if (empleados.equals(tareas.getEmpleados())) {
                tareasEmpleado.add(tareas);
            }
        }
        return tareasEmpleado;
    }

}
