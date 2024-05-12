package excepciones;

/**
 * @author Jordi Gisbert Ferriz
 */

public class MaximoIntentosAlcanzadosExcepcion extends RuntimeException  {

    public MaximoIntentosAlcanzadosExcepcion() {
        super("Error Login: Se ha alcanzado el número máximo de intentos para iniciar sesión.");
    }
}
