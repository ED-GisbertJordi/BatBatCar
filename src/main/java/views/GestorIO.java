package views;

/**
 * IMPORTANTE: Esta clase está dedicada para la entrada información por parte
 * del usuario. No se debe declarar un objeto "Scanner" en ninguna otra parte
 * del código. Siempre que quieras solicitar un dato, haz uso de uno de los 
 * métodos aquí establecidos (puedes añadir nuevos, si lo crees conveniente).
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorIO {

    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static int getInt(String mensaje) {
        do {
            System.out.print(mensaje+": ");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            System.out.println("Debe introducir un entero");
            scanner.next();
        } while (true);
    }
    
    public static int getInt(String mensaje, int min, int max) {
         do {
            int numero = getInt(mensaje);
            if (numero >= min && numero <= max) {
                return numero;
            }
            System.out.println("Error! Número fuera de rango");
        } while (true);
    }

    public static float getFloat(String mensaje) {
        do {
            System.out.print(mensaje+": ");
            if (scanner.hasNextFloat()) {
                return scanner.nextFloat();
            }
            System.out.println("Debe introducir un número decimal");
            scanner.next();
        } while (true);
    }

    public static String getString(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.next();
    }

    public static boolean confirmar(String mensaje) {
        do {
            System.out.print(mensaje + "[S/N]: ");
            String respuesta = scanner.next().toUpperCase();
            if (respuesta.equals("S")) {
                return true;
            } else if (respuesta.equals("N")) {
                return false;
            }

            System.out.println("¡Error! Debe introducir S o N");
        } while (true);
    }
    
    public static LocalDateTime getFecheHora() {
        do {
            System.out.print("Introduzca la fecha (Ej: 10-10-2024): ");
            String fechaStr = scanner.nextLine();

            System.out.print("Introduzca la hora (Ej: 23:15): ");
            String horaStr = scanner.nextLine();

            String fechaHoraStr = fechaStr + "T" + horaStr;
            try {
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formateador);
                return fechaHora;
            } catch (Exception e) {
                System.out.println("Error al parsear la fecha o la hora.");
            }
        } while (true);
    }
    
    public static void print(String mensaje) {
        System.out.println(mensaje);
    }
}