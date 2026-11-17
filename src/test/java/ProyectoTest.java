import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProyectoTest {

    @Test
    void rechazaPresupuestoNegativo() {
        Cliente cliente = new Cliente(1, "Rafa", "rafa@empresa.com", null);

        assertThrows(IllegalArgumentException.class, () -> {
            new Proyecto(1, "Proyecto de prueba", new BigDecimal("-10.00"), cliente);
        });
    }

    @Test 
    void guardarPresupuestoPositivo() {
        Cliente cliente1 = new Cliente(5, "Robe", "robe@empresa.com", null);
       Proyecto proyecto1 =  new Proyecto(1, "Proyecto de prueba", new BigDecimal("100.00"), cliente1);
         assertEquals(new BigDecimal("100.00"), proyecto1.getPresupuesto());
    }

    @Test 
    void guardarPresupuestoCero() {
        Cliente cliente1 = new Cliente(5, "Robe", "robe@empresa.com", null);
       Proyecto proyecto1 =  new Proyecto(1, "Proyecto de prueba", BigDecimal.ZERO, cliente1);
         assertEquals(BigDecimal.ZERO, proyecto1.getPresupuesto());
    }

    @Test
    void sumarPresupuestos () {
        Cliente cliente = new Cliente(1, "Perez", "perez@f", null);
        Proyecto proyecto1 =  new Proyecto(1, "Proyecto de prueba", new BigDecimal("200"), cliente);
        Proyecto proyecto2 =  new Proyecto(2, "Proyecto de prueba", new BigDecimal("100"), cliente);
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        proyectos.add(proyecto1);
        proyectos.add(proyecto2);
        
        BigDecimal totalSuma = ProyectoService.sumarPresupuestos(proyectos);

        assertEquals(new BigDecimal("300"), totalSuma);
    }
    @Test 
    void sumarPresupuestosListaVacia (){
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        BigDecimal totalSuma = ProyectoService.sumarPresupuestos(proyectos);
        assertEquals(BigDecimal.ZERO, totalSuma);
    }

    @Test
    void sumarPresupuestosConDecimales () {
        Cliente cliente = new Cliente(1, "Perez", "perez@f", null);
        Proyecto proyecto1 =  new Proyecto(1, "Proyecto de prueba", new BigDecimal(".20"), cliente);
        Proyecto proyecto2 =  new Proyecto(2, "Proyecto de prueba", new BigDecimal(".10"), cliente);
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        proyectos.add(proyecto1);
        proyectos.add(proyecto2);
        
        BigDecimal totalSuma = ProyectoService.sumarPresupuestos(proyectos);

        assertEquals(new BigDecimal(".30"), totalSuma);
    }
}