package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class ViajeNoValidoException extends Exception {

    public ViajeNoValidoException() {
        super("Error: Viaje no valido.");
    }
}
