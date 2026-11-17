import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProyectoService {
    public static BigDecimal calcularPresupuestoTotalCliente(Cliente cliente, Connection conexion) throws SQLException{
        ArrayList<Proyecto> proyectosCliente = ProyectoDAO.obtenerTodosLosProyectosDeUnCliente(cliente, conexion);
     
        return sumarPresupuestos(proyectosCliente);
    }

    public static BigDecimal sumarPresupuestos(ArrayList<Proyecto> proyectos){
        BigDecimal totalPresupuesto = BigDecimal.ZERO;

        for (Proyecto proyecto : proyectos) {
            totalPresupuesto = totalPresupuesto.add(proyecto.getPresupuesto());
        }
        
        return totalPresupuesto;
    }
}
