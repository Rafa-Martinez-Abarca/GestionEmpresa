/**
 * Empleado con años de experiencia y un indicador de actividad.
 */
public class Desarrollador extends Empleados {

    private int anosExperiencia;
    private boolean activo;

    /**
     * Crea un desarrollador activo. Valida los datos comunes y rechaza experiencia negativa.
     */
    public Desarrollador(int id, String nombre, String email, double salario, int anosExperiencia) {
        super(id, nombre, email, salario);

        if (anosExperiencia < 0) {

            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos.");
        }

        this.anosExperiencia = anosExperiencia;
        activo = true;

    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return super.toString() + " Años de experiencia: " + anosExperiencia + " Activo: " + activo;
    }
}
