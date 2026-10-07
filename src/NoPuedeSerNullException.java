/**
 * Excepción unchecked para valores nulos no admitidos. No obliga a declarar throws ni a capturarla.
 */
public class NoPuedeSerNullException extends RuntimeException {
    public NoPuedeSerNullException(String mensaje) {
        super(mensaje);
    }
}
