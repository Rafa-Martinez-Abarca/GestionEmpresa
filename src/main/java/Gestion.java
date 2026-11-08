import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Coordina los registros de la empresa y las consultas que abarcan varios proyectos.
 */
public class Gestion {
    private  Registro<Cliente> listaClientes = new Registro<>(); 
    private  Registro<Empleados> listaEmpleados = new Registro<>(); 
    private  Registro<Proyecto> listaProyectos = new Registro<>(); 
    
    /**
     * Registra un cliente. Rechaza null y los ids negativos o repetidos.
     */
    public void agregarClientes(Cliente cliente) {

        if (cliente == null) {
            throw new NoPuedeSerNullException("No se pueden pasar parámetros nulos.");
        }
       
        listaClientes.agregar(cliente.getId(), cliente);
    }

    /**
     * Registra un empleado de cualquier subtipo. Rechaza null e ids negativos o repetidos.
     */
    public void agregarEmpleados(Empleados empleados) {

        if (empleados == null) {
            throw new NoPuedeSerNullException("No se pueden pasar parámetros nulos.");
        }
        
        listaEmpleados.agregar(empleados.getId(), empleados);
    }

    /**
     * Registra un proyecto cuyo cliente ya debe estar registrado por id.
     *
     * @throws NoPuedeSerNullException si el proyecto es null
     * @throws IllegalArgumentException si falta el cliente, no está registrado o el id es inválido o repetido
     */
    public void agregarProyectos(Proyecto proyecto) {

        if (proyecto == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }

        if (proyecto.getCliente() == null) {
            throw new IllegalArgumentException("Un proyecto tiene que pertenecer a un cliente.");
        }

       listaClientes.buscarPorID(proyecto.getCliente().getId());

        listaProyectos.agregar(proyecto.getId(), proyecto);
    }

    /**
     * Devuelve el cliente registrado; lanza IllegalArgumentException si el id no existe.
     */
    public Cliente buscarClientePorId(int id) {
        
        return listaClientes.buscarPorID(id);
    }

    /**
     * Devuelve el empleado registrado; lanza IllegalArgumentException si el id no existe.
     */
    public Empleados buscarEmpleadosPorId(int id) {
       
        return listaEmpleados.buscarPorID(id);
    }

    /**
     * Devuelve el proyecto registrado; lanza IllegalArgumentException si el id no existe.
     */
    public Proyecto buscarProyectoPorId(int id) {
      
        return listaProyectos.buscarPorID(id);
    }

    /**
     * Devuelve una lista nueva con los proyectos del cliente, comparado por id.
     * La lista queda vacía si no tiene proyectos. Rechaza un cliente null.
     */
    public ArrayList<Proyecto> obtenerTodosLosProyectosDeUnCliente(Cliente cliente) {
        if (cliente == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }
        ArrayList<Proyecto> proyectosCliente = new ArrayList<>();
        for (Proyecto proyecto : listaProyectos.obtenerTodos()) {
            if (proyecto.getCliente().equals(cliente)) {
                proyectosCliente.add(proyecto);
            }
        }
        return proyectosCliente;
    }

    /**
     * Devuelve el proyecto con más horas reales, o null si no hay proyectos.
     * En un empate conserva el primero recorrido; el orden no está garantizado.
     */
    public Proyecto encontrarProyectoConMasHorasReales() {
        Proyecto proyectoCandidato = null;
        for (Proyecto proyecto2 : listaProyectos.obtenerTodos()) {
            if (proyectoCandidato == null
                    || proyecto2.calcularHorasTotales() > proyectoCandidato.calcularHorasTotales()) {
                proyectoCandidato = proyecto2;
            }
        }
        return proyectoCandidato;
    }

    /**
     * Suma el presupuesto de los proyectos activos. Devuelve cero si no hay ninguno.
     */
    public double calcularPresupuestoTotalProyectosActivos() {
        double cantidadTotal = 0;

        for (Proyecto proyecto : listaProyectos.obtenerTodos()) {
            if (proyecto.getEstadoProyecto() == EstadoProyecto.ACTIVO) {
                cantidadTotal += proyecto.getCosteTotal();
            }
        }
        return cantidadTotal;
    }

    /**
     * Devuelve los clientes con un número de proyectos estrictamente mayor que cantidad.
     *
     * @throws IllegalArgumentException si cantidad es negativa
     */
    public ArrayList<Cliente> obtenerClienteConMasDeXProyectos(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("No se admiten valores negativos.");
        }
        HashMap<Cliente, Integer> proyectosPorCliente = new HashMap<>();
        ArrayList<Cliente> listaConClientes = new ArrayList<>();
        for (Proyecto proyecto : listaProyectos.obtenerTodos()) {
            proyectosPorCliente.put(proyecto.getCliente(),
                    proyectosPorCliente.getOrDefault(proyecto.getCliente(), 0) + 1);
        }

        for (Map.Entry<Cliente, Integer> entrada : proyectosPorCliente.entrySet()) {
            if (entrada.getValue() > cantidad) {
                listaConClientes.add(entrada.getKey());
            }
        }

        return listaConClientes;
    }

    /**
     * Suma los presupuestos de todos los proyectos por cliente, sin filtrar estados.
     * Devuelve null si no hay proyectos. En empate el resultado no está determinado.
     */
    public Cliente clienteConMayorPresupuestoAcumulado() {
        Cliente clienteConMayorGasto = null;
        HashMap<Cliente, Double> sumaDeCantidad = new HashMap<>();
        double compararGastos = 0;
        for (Proyecto proyecto : listaProyectos.obtenerTodos()) {
            sumaDeCantidad.put(proyecto.getCliente(),
                    sumaDeCantidad.getOrDefault(proyecto.getCliente(), 0.0) + proyecto.getCosteTotal());
        }

        for (Map.Entry<Cliente, Double> entrada : sumaDeCantidad.entrySet()) {
            if (entrada.getValue() >= compararGastos) {
                compararGastos = entrada.getValue();
                clienteConMayorGasto = entrada.getKey();
            }
        }
        return clienteConMayorGasto;
    }

    /**
     * Reúne en una lista nueva las tareas del empleado en todos los proyectos.
     * Rechaza null y devuelve una lista vacía si no tiene tareas.
     */
    public ArrayList<Tareas> obtenerTodasLasTareasDeEmpleado(Empleados empleados) {

        if (empleados == null) {
            throw new NoPuedeSerNullException("No se admiten valores nulos.");
        }
        ArrayList<Tareas> tareasEmpleado = new ArrayList<>();

        for (Proyecto proyecto2 : listaProyectos.obtenerTodos()) {

            tareasEmpleado.addAll(proyecto2.obtenerTareasDeEmpleado(empleados));

        }
        return tareasEmpleado;
    }

    /**
     * Devuelve el empleado registrado con más tareas completadas.
     * Devuelve null si ninguno tiene tareas completadas; los empates no tienen orden garantizado.
     */
    public Empleados encontrarEmpleadoConMasTareasCompletadas() {
        Empleados empleadoConTareasCompletadas = null;
        HashMap<Empleados, Integer> listaDeTareasCompletadas = new HashMap<>();
        int maximoTareasCompletadas = 0;

        for (Empleados empleados : listaEmpleados.obtenerTodos()) {
            ArrayList<Tareas> tareasDelEmpleado = obtenerTodasLasTareasDeEmpleado(empleados);

            for (Tareas tareas : tareasDelEmpleado) {
                if (tareas.getEstadoTarea() == EstadoTarea.COMPLETADA) {
                    listaDeTareasCompletadas.put(tareas.getEmpleados(),
                            listaDeTareasCompletadas.getOrDefault(tareas.getEmpleados(), 0) + 1);
                }
            }
        }

        for (Map.Entry<Empleados, Integer> entrada : listaDeTareasCompletadas.entrySet()) {

            if (entrada.getValue() > maximoTareasCompletadas) {
                maximoTareasCompletadas = entrada.getValue();
                empleadoConTareasCompletadas = entrada.getKey();
            }
        }

        return empleadoConTareasCompletadas;
    }

    /**
     * Devuelve empleados registrados que no están en ningún equipo, incluidos los proyectos cerrados.
     */
    public HashSet<Empleados> empleadosSinProyecto() {
        HashSet<Empleados> empleadosAsignados = new HashSet<>();
        HashSet<Empleados> empleadosSinProyecto = new HashSet<>();

        for (Proyecto proyecto : listaProyectos.obtenerTodos()) {
            empleadosAsignados.addAll(proyecto.obtenerEmpleadosPorProyecto());
        }

        for (Empleados empleados : listaEmpleados.obtenerTodos()) {
            if (!empleadosAsignados.contains(empleados)) {
                empleadosSinProyecto.add(empleados);
            }
        }
        return empleadosSinProyecto;
    }

    /**
     * Busca ambos objetos registrados y añade el empleado al equipo del proyecto.
     *
     * @throws IllegalArgumentException si algún id no existe o el empleado ya está en el equipo
     */
    public void agregarEmpleadoAProyecto(int idProyecto, int idEmpleado) {
        Proyecto proyecto = buscarProyectoPorId(idProyecto);
        Empleados empleado = buscarEmpleadosPorId(idEmpleado);

        proyecto.agregarEmpleados(empleado);
    }

}
