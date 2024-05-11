package entidades;

/**
 *
 * Clase que representa a un usuario de la aplicación
 */
public class Usuario {
    private String nombre;
    private String contraseña;

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }
    
    @Override
    public boolean equals(Object usuario) {
        Usuario user = (Usuario) usuario;
        return user.nombre.equals(this.nombre) && user.contraseña.equals(this.contraseña);
        
    }
    
}
