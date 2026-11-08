/**
 * Empleado que describe sus pruebas, herramientas y número de errores detectados.
 */
public class Tester extends Empleados {
    private String tipoPrueba;
    private String herramientas;
    private int erroresDetectados;

    /**
     * Valida los datos comunes y rechaza un número negativo de errores detectados.
     */
    public Tester(int id, String nombre, String email, double salario, String tipoPrueba, String herramientas,
            int erroresDetectados) {
        super(id, nombre, email, salario);
        this.tipoPrueba = tipoPrueba;
        this.herramientas = herramientas;
        if (erroresDetectados < 0) {
            throw new IllegalArgumentException("El número de errores detectados no puede ser negativo.");
        }
        this.erroresDetectados = erroresDetectados;
    }

    public String getTipoPrueba() {
        return tipoPrueba;
    }

    public String getHerramientas() {
        return herramientas;
    }

    public int getErroresDetectados() {
        return erroresDetectados;
    }

    @Override
    public String toString() {
        return super.toString() + " Tipo de pruebas: " + tipoPrueba + " Herramientas usadas: " + herramientas
                + " Errores detectados: " + erroresDetectados;
    }
}
