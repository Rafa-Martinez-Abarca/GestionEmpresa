import java.util.Scanner;

public class EntradaConsola {
    public static int leerEnter(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        while (true) {
            if (sc.hasNextInt()) {
                int cantidad = sc.nextInt();
                sc.nextLine();
                return cantidad;
            } else {
                System.out.println("Debes pasar un entero");
                sc.nextLine();
            }

        }
    }

    public static String leerTextoNoVacio(Scanner sc, String mesaje) {
        System.out.println(mesaje);

        while (true) {
            String nombre = sc.nextLine();
            if (nombre.isBlank()) {
                System.out.println("No se puede dejar el campo vacío");

            } else {
                return nombre;
            }
        }
    }

    public static int leerEnteroNoNegativo(Scanner sc, String mensaje) {

        while (true) {
            int cantidad = leerEnter(sc, mensaje);

            if (cantidad >= 0) {
                return cantidad;
            } else {
                System.out.println("No se pueden pasar valores negativos.");
            }
        }

    }

    public static String leerNombre(Scanner sc, String mensaje) {

        while (true) {
            String leerNombre = leerTextoNoVacio(sc, mensaje);
            boolean distintoAlgunDigito = true;
            for (int i = 0; i < leerNombre.length(); i++) {
                if (Character.isDigit(leerNombre.charAt(i))) {
                    distintoAlgunDigito = false;
                }
            }
            if (distintoAlgunDigito) {
                return leerNombre;
            } else {
                System.out.println("No se pueden pasar numeros al nombre");

            }
        }

    }
}
