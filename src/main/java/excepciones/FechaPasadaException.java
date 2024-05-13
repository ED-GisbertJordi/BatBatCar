package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class FechaPasadaException extends Exception {

    public FechaPasadaException() {
        super("Error: La fecha es anterior a la actual.");
    }
}
