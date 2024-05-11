package gestores;

import java.util.HashSet;
import java.util.Iterator;

import entidades.Usuario;

/**
 * @author Jordi Gisbert Ferriz
 */
public class UsuariosManager {

    private HashSet<Usuario> usuarios;

    public UsuariosManager() {
        usuarios = new HashSet<>();
        init();
    }

    public boolean add(Usuario reserva) {
        return usuarios.add(reserva);
    }

    public boolean remove(Usuario reserva) {
        return usuarios.remove(reserva);
    }

    public boolean existente(Usuario usuario) {
        for (Usuario next : usuarios) {
            if (next.equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    public void init() {
        usuarios.add(new Usuario("Jordi", "1234"));
        usuarios.add(new Usuario("Anon", "1234"));
    }

}
