/**
 * Excepción checked al añadir tareas a un proyecto cerrado. Quien llama debe capturarla o declararla.
 */
public class ProyectoCerrado extends Exception {
    public ProyectoCerrado(String mensaje) {
        super(mensaje);
    }
}
