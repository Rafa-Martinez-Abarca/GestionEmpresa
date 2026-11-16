import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProyectoService {
    public static BigDecimal calcularPresupuestoTotalCliente(Cliente cliente, Connection conexion) throws SQLException{
        ArrayList<Proyecto> proyectosCliente = ProyectoDAO.obtenerTodosLosProyectosDeUnCliente(cliente, conexion);
        BigDecimal totalPresupuestos = BigDecimal.ZERO;

        for (Proyecto proyectos : proyectosCliente) {
          totalPresupuestos = totalPresupuestos.add(proyectos.getPresupuesto());
        }

        return totalPresupuestos;
    }
}
