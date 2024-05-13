package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class ReservaNoValidaException extends Exception {

    public ReservaNoValidaException() {
        super("Error: Reserva no valida.");
    }
}
