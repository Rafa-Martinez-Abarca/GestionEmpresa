import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {
     public static Cliente obtenerCliente(int id, Connection conexion) throws SQLException{
    String sql = "SELECT * FROM cliente WHERE id = ?";

    try(PreparedStatement consulta = conexion.prepareStatement(sql)){
        consulta.setInt(1, id);

        try(ResultSet resultado = consulta.executeQuery()){

            if (resultado.next()) {
              int idCliente =  resultado.getInt("id");
              String nombre = resultado.getString("nombre");
              String email = resultado.getString("email");
              
              return new Cliente(idCliente, nombre, email, null);
            }else{
                return null;
            }
            
        }
    }
  }

  public static ArrayList<Cliente> obtenerClientePorNombre(String nombre, Connection conexion) throws SQLException{
    String sql = "SELECT * FROM cliente WHERE nombre ILIKE ?";

    try(PreparedStatement consulta = conexion.prepareStatement(sql)){
        consulta.setString(1, "%" + nombre + "%");
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try(ResultSet resultado = consulta.executeQuery()){
            while (resultado.next()) {
        
                int id = resultado.getInt("id");
                String nombreCliente = resultado.getString("nombre");
                String email = resultado.getString("email");
               Cliente cliente = new Cliente(id, nombreCliente, email, null);
                listaClientes.add(cliente);
            }
            return listaClientes;
         }
     }
    }
    
    
        public static boolean existeClientePorId(int idCliente, Connection conexion) throws SQLException{
        String sql = "SELECT * FROM cliente WHERE id = ?";

        

        try( PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, idCliente);

             try(ResultSet resultado = consulta.executeQuery()) {
         
        return resultado.next();

    }
        } 

  } 

   public static int actualizarEmailCliente(int id, String email, Connection conexion) throws SQLException{
        String sql = "UPDATE cliente SET email = ? WHERE id = ?";
        
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setString(1,email);
            consulta.setInt(2, id);

             int totalFilas = consulta.executeUpdate();

             return totalFilas;
        }
    }

      public static int insertarCliente(String nombre, String email, Connection conexion) throws SQLException{
        String sql = "INSERT INTO cliente(nombre, email) VALUES (?, ?)";

        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setString(1, nombre);
            consulta.setString(2, email);

            int totalFilas = consulta.executeUpdate();

            return totalFilas;
        }
    }

    public static int eliminarClientePorId(int id, Connection conexio) throws SQLException{
        String sql = "DELETE FROM cliente WHERE id = ? ";

        try(PreparedStatement consulta = conexio.prepareStatement(sql)){
            consulta.setInt(1, id);

            int totalFilas = consulta.executeUpdate();

            return totalFilas;
        }
    }

      public static ArrayList<Cliente> clientesSinProyecto(Connection conexion) throws SQLException{
        String sql = "SELECT cliente.id, cliente.nombre,cliente.email FROM cliente LEFT JOIN proyecto ON cliente.id = proyecto.cliente_id WHERE proyecto.id IS NULL";
        
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            ArrayList<Cliente> clienteSinProyecto = new ArrayList<>();
            
            try(ResultSet resultado = consulta.executeQuery()){
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    String email = resultado.getString("email");
                    Cliente cliente = new Cliente(id, nombre, email, null);

                    clienteSinProyecto.add(cliente);
                }

                return clienteSinProyecto;
            }
        }
    
    }
    public static int totalProyectosCliente(Cliente cliente, Connection conexion) throws SQLException {
        String sql = "SELECT  COUNT(*) AS total_proyecto FROM proyecto WHERE proyecto.cliente_id = ?";
        
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
           consulta.setInt(1, cliente.getId());

            try(ResultSet resultado = consulta.executeQuery()){
                if (resultado.next()) {
                    int total = resultado.getInt("total_proyecto");
                    return total;
                }else{
                    return 0;
                }
            }
        }
    
    }


    public static ArrayList<Cliente> clientesConXCantidadDeProyectos(int num, Connection conexion) throws SQLException{
        String sql = "SELECT cliente.id, cliente.nombre, cliente.email FROM cliente LEFT JOIN proyecto ON cliente.id = proyecto.cliente_id GROUP BY cliente.id, cliente.nombre, cliente.email HAVING COUNT(proyecto.cliente_id) >= ?";
          
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, num);

            try(ResultSet resultado = consulta.executeQuery()){
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    String email = resultado.getString("email");
                    Cliente cliente = new Cliente(id, nombre, email, null);

                    clientes.add(cliente);
                }
                return clientes;
            }
        }
    }

       public static ArrayList<Cliente> listaClienteConPresupuestoMayorA(int num, Connection conexion) throws SQLException{
        String sql = "SELECT cliente.id, cliente.nombre,cliente.email FROM cliente WHERE EXISTS( SELECT 1 FROM proyecto WHERE proyecto.cliente_id = cliente.id AND proyecto.presupuesto > ?)";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, num);

            try(ResultSet resultado = consulta.executeQuery()){
                while (resultado.next()) {
                  int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    String email = resultado.getString("email");
                    Cliente cliente = new Cliente(id, nombre, email, null);

                    clientes.add(cliente);  
                }
                return clientes;
            }
        }
    }

       public static ArrayList<Cliente> obtenerClientesConTodosLosProyectosDesdePresupuesto(int num, Connection conexion) throws SQLException{
        String sql = "SELECT cliente.id, cliente.nombre,cliente.email FROM cliente WHERE NOT EXISTS(SELECT 1 FROM proyecto WHERE proyecto.cliente_id = cliente.id AND proyecto.presupuesto < ?) AND EXISTS (SELECT 1 FROM proyecto WHERE proyecto.cliente_id = cliente.id)";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(PreparedStatement consulta = conexion.prepareStatement(sql)){
            consulta.setInt(1, num);

            try(ResultSet resultado = consulta.executeQuery()){
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    String email = resultado.getString("email");
                    Cliente cliente = new Cliente(id, nombre, email, null);

                    clientes.add(cliente);
                }
                return clientes;
            }
        }
    }

    
 public static ArrayList<Cliente> obtenerClientesPorNombreYMinimoProyectos(String nombre, int num, Connection conexion) throws SQLException{
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Cliente> clientesCoincidentes = ClienteDAO.obtenerClientePorNombre(nombre, conexion);
    
         
         for (Cliente clientes1 : clientesCoincidentes) {

            int totalProyecto = ClienteDAO.totalProyectosCliente(clientes1, conexion);
            if(totalProyecto >= num){
            clientes.add(clientes1);
         } 
         
        }
      
        return clientes;
    }

     public static ArrayList<Cliente> buscarCliente(int idMinimo, String texto, Connection conexion) throws SQLException{
        String sql = "SELECT * FROM cliente WHERE id > ? AND nombre ILIKE ?";
        ArrayList<Cliente> clientes = new ArrayList<>();
          
        try (
            PreparedStatement consulta = conexion.prepareStatement(sql)){

                consulta.setInt(1, idMinimo);
                consulta.setString(2, "%" + texto + "%");
                
      
                
          try(ResultSet resultado = consulta.executeQuery()) {
        
        

         while (resultado.next()) {
        int id = resultado.getInt("id");
        String nombre = resultado.getString("nombre");
        String email = resultado.getString("email");
           Cliente cliente = new Cliente(id, nombre, email, null);
           clientes.add(cliente);
        
        }
     return clientes;
        
     }
    }
}

}
