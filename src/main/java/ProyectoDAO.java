import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class ProyectoDAO {
    public static BigDecimal devolverPresupuestoPorId(int id, Connection conexion) throws SQLException {
        String sql = "SELECT presupuesto FROM proyecto WHERE id = ?";
        BigDecimal presupuesto = null;
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    presupuesto = resultado.getBigDecimal("presupuesto");
                }

                return presupuesto;
            }
        }
    }

    public static int actualizarPresupuesto(int id, BigDecimal presupuesto, Connection conexion) throws SQLException {
        String sql = "UPDATE proyecto SET presupuesto = ? WHERE id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setBigDecimal(1, presupuesto);
            consulta.setInt(2, id);

            int totalFilas = consulta.executeUpdate();
            return totalFilas;
        }
    }

    public static Proyecto obtenerProyecto(int id, Connection conexion) throws SQLException {
        String sql = "SELECT * FROM proyecto WHERE id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    int idProyecto = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    int idCliente = resultado.getInt("cliente_id");
                    Cliente cliente = ClienteDAO.obtenerCliente(idCliente, conexion);
                    BigDecimal presupuesto = resultado.getBigDecimal("presupuesto");
                    Proyecto proyecto2 = new Proyecto(idProyecto, nombre, presupuesto, cliente);
                    return proyecto2;
                }
                return null;
            }
        }
    }
        public static ArrayList<Proyecto> obtenerTodosLosProyectosDeUnCliente(Cliente cliente, Connection conexion) throws SQLException{
            String sql = "SELECT id, nombre, presupuesto FROM proyecto WHERE cliente_id = ?";
            ArrayList<Proyecto> listaDeProyectos = new ArrayList<>();
            
            try (PreparedStatement consulta = conexion.prepareStatement(sql)){
                consulta.setInt(1, cliente.getId());
                try(ResultSet resultado = consulta.executeQuery()){
                while (resultado.next()) {
                    int idProyecto = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    BigDecimal presupuesto = resultado.getBigDecimal("presupuesto");
                    Proyecto proyecto = new Proyecto(idProyecto, nombre, presupuesto, cliente);
                    listaDeProyectos.add(proyecto);
                }
                return listaDeProyectos;
                }
            } 
        }

        public static int insertarProyecto(String nombre, BigDecimal presupuesto, Cliente cliente, Connection conexion) throws SQLException{
            String sql = "INSERT INTO proyecto (nombre, presupuesto, cliente_id) VALUES (?, ?, ?)";

            try(PreparedStatement consulta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                consulta.setString(1, nombre);
                consulta.setBigDecimal(2, presupuesto);
                consulta.setInt(3, cliente.getId());

                consulta.executeUpdate();

                try(ResultSet resultado = consulta.getGeneratedKeys()){
                    if (resultado.next()) {
                        int id = resultado.getInt("id");

                        return id;
                    }
                }
                throw new SQLException("No se pudo recuperar el id del proyecto insertado.");
            }
        }
    
}
