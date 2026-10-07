/**
 * Demostración por consola con datos de ejemplo y pruebas manuales de errores esperados.
 */
public class Main {
    /**
     * Ejecuta los ejemplos. Cada error previsto se captura para continuar con las demás pruebas.
     *
     * @param args argumentos de consola; no se utilizan
     * @throws ProyectoCerrado si una carga inicial intenta añadir tareas a un proyecto cerrado
     */
    public static void main(String[] args) throws ProyectoCerrado {

        // 1. Creación de datos y asignaciones.
        Gestion gestion = new Gestion();

        Cliente cliente1 = new Cliente(1, "Rafa", "das@gmail.com", "Sonoco");
        Cliente cliente2 = new Cliente(2, "Pepe", "rrhh@gmail.com", "Plastidecor");
        Cliente cliente3 = new Cliente(3, "Lopez", "dssd@gmail.com", "Fini");

        Proyecto proyecto1 = new Proyecto(1, "Banco", 500, cliente1);
        Proyecto proyecto2 = new Proyecto(2, "Diapositivas", 200, cliente1);
        Proyecto proyecto3 = new Proyecto(3, "e-commerce", 1500, cliente2);
        Proyecto proyecto4 = new Proyecto(4, "Tareas comunes", 200, cliente2);
        Proyecto proyecto6 = new Proyecto(5, "Tareas comunes", 200, cliente2);

        Tareas tareas1 = new Tareas(2, "Login", "Hacer que una persona introduzca su usuario y contraseña.", 11, 2,
                Prioridad.MEDIA);
        Tareas tareas2 = new Tareas(3, "Diseño", "Diseñar la página.", 30, 2, Prioridad.MEDIA);
        Tareas tareas3 = new Tareas(4, "Lógica", "Hacer la lógica de algunas funciones", 80, 100, Prioridad.ALTA);
        Tareas tareas4 = new Tareas(5, "Maquetación", "Hacer la maquetación", 90, 130, Prioridad.ALTA);
        Tareas tareas5 = new Tareas(6, "Ventanas", "Distribución de ventanas", 300, 140, Prioridad.CRITICA);
        Tareas tareas6 = new Tareas(7, "Foot", "Diseño de la parte baja de la página", 120, 100, Prioridad.BAJA);
        Tareas tareas7 = new Tareas(8, "Header", "Diseño de la cabecera", 30, 110, Prioridad.ALTA);

        Desarrollador empleados1 = new Desarrollador(1, "Rafa", "rr@gmail.com", 2000.0, 5);
        Desarrollador empleados2 = new Desarrollador(2, "Pepe", "dhd@gmail.com", 2000.0, 9);
        Desarrollador empleados3 = new Desarrollador(3, "Leo", "ddd@gmail.com", 2000.0, 1);
        Tester empleados4 = new Tester(4, "Rocio", "asa@gmail.com", 123, "Comprar con cliente", "..", 20);
        ProjectManager empleados5 = new ProjectManager(5, "Jose", "dsa@gmail.com", 5000, true, "Asignar tareas a empleados.");

        // Id repetido intencionadamente para probar que no sustituye al empleado original.
        Desarrollador empleados6 = new Desarrollador(1, "Jose", "ded@gmail.", 455, 1);

        tareas1.setEstadoTarea(EstadoTarea.EN_PROGRESO);
        tareas2.setEstadoTarea(EstadoTarea.EN_PROGRESO);
        tareas3.setEstadoTarea(EstadoTarea.EN_PROGRESO);
        tareas4.setEstadoTarea(EstadoTarea.EN_PROGRESO);

        tareas1.setEstadoTarea(EstadoTarea.COMPLETADA);
        tareas2.setEstadoTarea(EstadoTarea.COMPLETADA);
        tareas3.setEstadoTarea(EstadoTarea.COMPLETADA);

        // Registrar antes de formar equipos permite comprobar que los ids existen.
        gestion.agregarClientes(cliente1);
        gestion.agregarClientes(cliente2);
        gestion.agregarClientes(cliente3);

        gestion.agregarEmpleados(empleados1);
        gestion.agregarEmpleados(empleados2);
        gestion.agregarEmpleados(empleados4);
        gestion.agregarEmpleados(empleados3);
        gestion.agregarEmpleados(empleados5);

        gestion.agregarProyectos(proyecto1);
        gestion.agregarProyectos(proyecto2);
        gestion.agregarProyectos(proyecto3);
        gestion.agregarProyectos(proyecto4);

        gestion.agregarEmpleadoAProyecto(1, 1);
        gestion.agregarEmpleadoAProyecto(1, 2);
        gestion.agregarEmpleadoAProyecto(2, 3);
        gestion.agregarEmpleadoAProyecto(3, 4);
        gestion.agregarEmpleadoAProyecto(3, 3);
        gestion.agregarEmpleadoAProyecto(4, 3);

        proyecto1.agregarTareas(tareas2);
        proyecto1.agregarTareas(tareas1);
        proyecto1.agregarTareas(tareas3);
        proyecto2.agregarTareas(tareas4);
        proyecto2.agregarTareas(tareas5);
        proyecto4.agregarTareas(tareas6);
        proyecto3.agregarTareas(tareas7);

        proyecto1.asignarTareasEmpleados(empleados1, tareas1);
        proyecto1.asignarTareasEmpleados(empleados2, tareas2);
        proyecto1.asignarTareasEmpleados(empleados2, tareas3);
        proyecto2.asignarTareasEmpleados(empleados3, tareas4);
        proyecto2.asignarTareasEmpleados(empleados3, tareas5);
        proyecto3.asignarTareasEmpleados(empleados3, tareas7);

        proyecto1.setEstadoProyecto(EstadoProyecto.ACTIVO);
        proyecto3.setEstadoProyecto(EstadoProyecto.ACTIVO);

        // 2. Consultas y estadísticas. Descomenta las consultas que quieras visualizar.
        System.out.println("Encontrar a un empleado con más tareas. " +
        gestion.encontrarEmpleadoConMasTareasCompletadas());
        System.out.println("Obtener todos los proyectos de un cliente. " +
        gestion.obtenerTodosLosProyectosDeUnCliente(cliente1));
        System.out.println("Obtener todas las tareas de un empleado. " + gestion.obtenerTodasLasTareasDeEmpleado(empleados3));
        System.out.println("Obtener los empleados de un proyecto. " +
        proyecto1.obtenerEmpleadosPorProyecto());
        System.out.println("Obtener tareas por estado. " +
        proyecto1.obtenerTareasPorEstado(EstadoTarea.COMPLETADA));
        System.out.println("Obtener las tareas de un empleado." +
        proyecto2.obtenerTareasDeEmpleado(empleados3));
        System.out.println("Obtener todas las tareas por prioridad de tareas. " +
        proyecto1.mostrarTareasPorPrioridadDeTareas());
        System.out.println("Calcular las horas totales del proyecto. " +
        proyecto1.calcularHorasTotales());
        System.out.println("Encontrar el proyecto con más horas reales. " +
        gestion.encontrarProyectoConMasHorasReales());
        System.out.println("Obtener al empleado con más tareas completadas. " +
        gestion.encontrarEmpleadoConMasTareasCompletadas());
        System.out.println("Obtener al cliente con más proyectos de una cantidad pasada. " + gestion.obtenerClienteConMasDeXProyectos(1));
        System.out.println("Calcular el presupuesto de los proyectos activos. " +
        gestion.calcularPresupuestoTotalProyectosActivos());
        System.out.println("Cliente con mayor presupuesto acumulado. " +
        gestion.clienteConMayorPresupuestoAcumulado());
        System.out.println("Empleados sin proyectos. " +
        gestion.empleadosSinProyecto());

         //3. Pruebas de errores. Los mensajes de los catch son resultados esperados.

        try {
            gestion.agregarEmpleados(empleados6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(gestion.buscarEmpleadosPorId(1));

        // Búsquedas con ids inexistentes.
        try {
            gestion.buscarClientePorId(99);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            gestion.buscarEmpleadosPorId(99);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            gestion.buscarProyectoPorId(99);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Rechazo de objetos nulos al registrarlos.
        try {
            gestion.agregarEmpleados(null);
        } catch (NoPuedeSerNullException e) {
            System.out.println(e.getMessage());
        }

        try {
            gestion.agregarClientes(null);
        } catch (NoPuedeSerNullException e) {
            System.out.println(e.getMessage());
        }

        try {
            gestion.agregarProyectos(null);
        } catch (NoPuedeSerNullException e) {
            System.out.println(e.getMessage());
        }

        // Valores fuera de rango: presupuesto, salario y horas.
        try {
            Proyecto proyecto5 = new Proyecto(4, "Tareas comunes", -200, cliente2);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Desarrollador empleados7 = new Desarrollador(8, "Jose", "ded@gmail.", -455, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Tareas tareas12 = new Tareas(12, "Solución de errores.", "Solucionar errores en el programa. ", 0, 4,
                    Prioridad.ALTA);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Tareas tareas15 = new Tareas(15, "Solución de errores.", "Solucionar errores en el programa. ", -5, 4,
                    Prioridad.ALTA);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Tareas tareas13 = new Tareas(13, "Solución de errores.", "Solucionar errores en el programa. ", 70, -4,
                    Prioridad.ALTA);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Tareas tareas13 = new Tareas(13, "Solución de errores.", "Solucionar errores en el programa. ", 70, 0,
                    Prioridad.ALTA);
            System.out.println(tareas13.getHorasReales());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Los ids repetidos tampoco deben sustituir clientes ni proyectos.
        Cliente cliente13 = new Cliente(3, "Rosa", "ras@gas", "Fini");
        try {
            gestion.agregarClientes(cliente13);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Proyecto proyecto13 = new Proyecto(3, "Conceptos básicos", 200, cliente1);
        try {
            gestion.agregarProyectos(proyecto13);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Asignaciones inválidas: empleado ajeno, tarea ajena o argumentos nulos.
        try {
            proyecto2.asignarTareasEmpleados(empleados5, tareas5);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            proyecto1.asignarTareasEmpleados(empleados1, tareas7);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            proyecto3.asignarTareasEmpleados(null, null);
        } catch (NoPuedeSerNullException e) {
            System.out.println(e.getMessage());
        }

        // Transiciones de estado: se rechaza finalizar con trabajo pendiente.
        try {
            proyecto2.setEstadoProyecto(EstadoProyecto.FINALIZADO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            tareas4.setEstadoTarea(EstadoTarea.EN_PROGRESO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Caso válido: todas las tareas del proyecto 1 ya están completadas.
        try {
            proyecto1.setEstadoProyecto(EstadoProyecto.FINALIZADO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // No se pueden añadir tareas a proyectos finalizados ni cancelados.
        Tareas tareas16 = new Tareas(16, "null", "null", 23, 24, Prioridad.BAJA);

        try {
            proyecto1.agregarTareas(tareas16);
        } catch (ProyectoCerrado e) {
            System.out.println(e.getMessage());
        }

        proyecto6.setEstadoProyecto(EstadoProyecto.CANCELADO);

        try {
            proyecto6.agregarTareas(tareas16);
        } catch (ProyectoCerrado e) {

            System.out.println(e.getMessage());
        }

        // Una tarea completada no se reabre y ningún estado puede ser null.
        try {
            tareas1.setEstadoTarea(EstadoTarea.EN_PROGRESO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            tareas4.setEstadoTarea(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Bajas inválidas: no pertenece al equipo, tiene trabajo pendiente o es null.
        try {
            proyecto3.eliminarEmpleado(empleados2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            proyecto3.eliminarEmpleado(empleados3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            proyecto2.eliminarEmpleado(null);
        } catch (NoPuedeSerNullException e) {
            System.out.println(e.getMessage());
        }

        // Comprobar que un empleado sin tareas aparece en el equipo y deja de aparecer
        // al eliminarlo del proyecto.
        Desarrollador desarrollador16 = new Desarrollador(16, "Rodi", "asd", 1343, 4);
        
        gestion.agregarEmpleados(desarrollador16);
        gestion.agregarEmpleadoAProyecto(3,16);
        

        System.out.println(proyecto3.obtenerEmpleadosPorProyecto() + "  Comprobar el siguiente.");

        proyecto3.eliminarEmpleado(desarrollador16);

        System.out.println(proyecto3.obtenerEmpleadosPorProyecto());

        System.out.println("El programa continúa.");
    }
}
