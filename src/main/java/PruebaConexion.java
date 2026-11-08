import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PruebaConexion {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost:5432/empresa_pruebas";
        String usuario = "gestion_app";
        String password = System.getenv("DB_PASSWORD");

        try (Connection conexion = DriverManager.getConnection(url, usuario, password)) {

            int opcion = -1;

            do {
                System.out.println("1. Buscar cliente por id.");
                System.out.println("2. Buscar cliente por nombre.");
                System.out.println("3. Buscar cliente sin proyecto.");
                System.out.println("4. Buscar clientes con proyectos con al menos la cantidad indicada");
                System.out.println("5. Buscar clientes por nombres y mínimo de proyectos");
                System.out.println("0. Salr.");

              opcion = EntradaConsola.leerEnter(sc, "Elige una llamada");
                    if (opcion > 5 || opcion < 0) {
                        System.out.println("Opción no válida");
                    }
                 

                if (opcion == 1) {
                    int id = EntradaConsola.leerEnter(sc, "Que id quieres buscar?");
                    if (id >= 0) {
                        mostrarClientePorId(id, conexion);
                    }else{
                        System.out.println("No puede ser una cantidad negativa");
                    }
                           
                }

                if (opcion == 2) {
                    String nombre = EntradaConsola.leerNombre(sc, "Que nombre quieres buscar?");
                    mostrarClientesPorNombre(nombre, conexion);

                }

                if (opcion == 3) {
                    mostrarClientesSinProyecto(conexion);
                }

                if (opcion == 4) {
                    int cantidad = EntradaConsola.leerEnteroNoNegativo(sc, "Que cantidad de proyectos debe tener?");
                    
                    clientesConXCantidadDeProyectos(cantidad, conexion);

                    
                }

                if (opcion == 5) {
                    String nombre = EntradaConsola.leerNombre(sc, "Que nombre quieres buscar?");
                    int cantidad = EntradaConsola.leerEnteroNoNegativo(sc, "Que cantidad quieres buscar?");
                          mostrarClientesPorNombreYMinimoProyectos(nombre, cantidad, conexion);
                    }    
            } while (opcion != 0);

        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    public static void mostrarClientesPorNombre(String texto, Connection conexion) throws SQLException {

            ArrayList<Cliente> listaClientesPorNombre = ClienteDAO.obtenerClientePorNombre(texto, conexion);
            if (listaClientesPorNombre.isEmpty()) {
                System.out.println("No hay clientes con ese nombre.");
            }

            for (Cliente listaCliente : listaClientesPorNombre) {
                System.out.println(listaCliente);
            }
    }

    public static void mostrarClientesSinProyecto(Connection conexion) throws SQLException {
        ArrayList<Cliente> clientesSinProyecto = ClienteDAO.clientesSinProyecto(conexion);
        if (clientesSinProyecto.isEmpty()) {
            System.out.println("No hay clientes sin proyecto.");
        }

        for (Cliente listaCliente : clientesSinProyecto) {
            System.out.println(listaCliente);
        }
    }

    public static void mostrarClientePorId(int id, Connection conexion) throws SQLException {

        Cliente cliente = ClienteDAO.obtenerCliente(id, conexion);
        // Comprobamos que no sea nulo
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
        } else {
            System.out.println(cliente);
        }

    }

    public static void clientesConXCantidadDeProyectos(int cantidad, Connection conexion) throws SQLException {
        ArrayList<Cliente> cliente = ClienteDAO.clientesConXCantidadDeProyectos(cantidad, conexion);

        if (cliente.isEmpty()) {
            System.out.println("No hay clientes con esa cantidad de proyectos.");
        } else {
            for (Cliente cliente2 : cliente) {
                System.out.println(cliente2);
            }
        }
    }

    public static void mostrarClientesPorNombreYMinimoProyectos(String nombre, int cantidad, Connection conexion)
            throws SQLException {

        ArrayList<Cliente> listaClientes = ClienteDAO.obtenerClientesPorNombreYMinimoProyectos(nombre, cantidad,
                conexion);
        if (listaClientes.isEmpty()) {
            System.out.println("Lista vacía");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println(cliente);
            }
        }

    }

 
    
}
