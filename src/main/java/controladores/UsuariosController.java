package controladores;

import entidades.Usuario;
import excepciones.*;
import gestores.UsuariosManager;
import views.GestorIO;

/**
 * @author Jordi Gisbert Ferriz
 */
public class UsuariosController {

    private UsuariosManager gestor;

    public UsuariosController() {
        gestor = new UsuariosManager();
    }
    
    

    public Usuario log() {
        final int INTENTOS = 3;
        for (int i = 0; i < INTENTOS; i++) {
            try{
                String name = GestorIO.getString("UserName");
                String pass = GestorIO.getString("Passeord");
                Usuario user = new Usuario(name, pass);
                if (!gestor.existente(user)) {
                    throw new CredencialesInvalidasExcepcion();
                }
                return user;
            }catch (CredencialesInvalidasExcepcion e){
                GestorIO.print(e.getMessage());
            }
            

        }
        GestorIO.print("Se ha alcanzado el número máximo de intentos. ");
        throw new MaximoIntentosAlcanzadosExcepcion();
    }

}
