import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClienteDAOTest {

    public static Connection abrirConexion() throws SQLException{
        Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/empresa_test", "gestion_app", System.getenv("DB_PASSWORD"));

        return conexion;
    }

    @Test
    void insertarYRecuperarCliente() throws SQLException {
        

        try (Connection conexion = abrirConexion()) {
            // Aquí añadiremos las operaciones de la prueba.
            conexion.setAutoCommit(false);
            try {
                String nombre = "Lore";
                String email = "rrpp@hosting";
                int idCliente = ClienteDAO.insertarCliente(nombre, email, conexion);
                Cliente cliente = ClienteDAO.obtenerCliente(idCliente, conexion);

                assertNotNull(cliente);
                assertEquals(nombre, cliente.getNombre());
                assertEquals(email, cliente.getEmail());

            } finally {
                conexion.rollback();
            }
        }
    }

    @Test
    void clienteConEmailRepetido() throws SQLException {
      

        try (Connection conexion = abrirConexion()) {

            conexion.setAutoCommit(false);
            try {
                String nombre = "Lore";
                String nombre1 = "Rafa";
                String email = "rrpp@hosting";
                int idCliente = ClienteDAO.insertarCliente(nombre, email, conexion);

                SQLException error = assertThrows(SQLException.class, () -> {
                    int idCliente2 = ClienteDAO.insertarCliente(nombre1, email, conexion);
                });
                assertEquals("23505", error.getSQLState());

            } finally {
                conexion.rollback();
            }

        }
    }

    @Test
    void modificarEmail() throws SQLException {
        try (Connection conexion = abrirConexion()) {

            try {
                conexion.setAutoCommit(false);
                String nombre = "Jose";
                String email = "jose@hao";

                int id = ClienteDAO.insertarCliente(nombre, email, conexion);
                int filasActualizadas = ClienteDAO.actualizarEmailCliente(id, "rras@d", conexion);
                    
                Cliente cliente = ClienteDAO.obtenerCliente(id, conexion);
                assertEquals("rras@d", cliente.getEmail());
                assertEquals(1, filasActualizadas);
                
                
                
            } finally {
                conexion.rollback();
            }
        }
    }

    @Test
    void noExisteCliente() throws SQLException {
        try (Connection conexion = abrirConexion()) {

            try {
                conexion.setAutoCommit(false);
 
                int filasActualizadas = ClienteDAO.actualizarEmailCliente(12, "rras@d", conexion);

                assertEquals(0, filasActualizadas);

                
            } finally {
                conexion.rollback();
            }
        }
    }

}