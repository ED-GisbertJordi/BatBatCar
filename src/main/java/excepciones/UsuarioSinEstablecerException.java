package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class UsuarioSinEstablecerException extends Exception {

    public UsuarioSinEstablecerException() {
        super("Usuario no establecido. Inicie sesión antes de realizar esta acción.");
    }
}
