import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteService {
    
     public static boolean cambiarEmailDeDosClientes(int num1, int num2, String email1, String email2, Connection conexion) throws SQLException{
         conexion.setAutoCommit(false);
          boolean transaccionResuelta = false;
         try{
            int resultado1 = ClienteDAO.actualizarEmailCliente(num1, email1, conexion);
            int resultado2 = ClienteDAO.actualizarEmailCliente(num2, email2, conexion);

            if(resultado1 == 1 && resultado2 == 1){
                conexion.commit();
                transaccionResuelta = true;
                return true;
            }else {
                conexion.rollback();
                transaccionResuelta = true;
                return false;
            }

            
            } catch (SQLException e){
                conexion.rollback();
                transaccionResuelta = true;
                throw e;
            } finally {
                if (transaccionResuelta) {
                    conexion.setAutoCommit(true);
                }
            }
            
    }

    public static boolean cambiarEmailYBorrarCliente(int num1, int num2, String texto, Connection conexion) throws SQLException{
        conexion.setAutoCommit(false);
        boolean transaccionResuelta = false;
        try{
        int resultado1 = ClienteDAO.actualizarEmailCliente(num1, texto, conexion);
        int resultado2 = ClienteDAO.eliminarClientePorId(num2, conexion);

        
            if(resultado1 == 1 && resultado2 == 1){
                conexion.commit();
                transaccionResuelta = true;
                return true;
                
            }else {
                conexion.rollback();
                transaccionResuelta = true;
                return false;
               
            }
        } catch (SQLException e){
            conexion.rollback();
            transaccionResuelta = true;
            throw e;
            
        } finally {
            if (transaccionResuelta) {
                conexion.setAutoCommit(true);
            }
        }
    }

    
}
