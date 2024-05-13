package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class ReservaNoCancelableException extends Exception {

    public ReservaNoCancelableException() {
        super("Error: Reserva no Cancelable.");
    }
}
